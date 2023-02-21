/*
Copyright 2017 - 2017 Amazon.com, Inc. or its affiliates. All Rights Reserved.
Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance with the License. A copy of the License is located at
    http://aws.amazon.com/apache2.0/
or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and limitations under the License.
*/

const express = require("express");
const bodyParser = require("body-parser");
const awsServerlessExpressMiddleware = require("aws-serverless-express/middleware");
const validator = require("./common/requestValidator");
const { validationResult } = require("express-validator");
const uuid = require("uuid");
const aws = require("./common/aws");
const db = require("./common/db");
const jwt = require("./common/jwt");

// declare a new express app
const app = express();
app.use(bodyParser.json());
app.use(awsServerlessExpressMiddleware.eventContext());

// Enable CORS for all methods
app.use(function (req, res, next) {
  res.header("Access-Control-Allow-Origin", "*");
  res.header("Access-Control-Allow-Headers", "*");
  next();
});

app.post("/user", validator.postUserValidation, async function (req, res) {
  db.pool.connect();
  const validationErrors = validationResult(req);
  if (!validationErrors.isEmpty()) {
    return res.status(400).json({ errors: validationErrors.array() });
  }

  try {
    const userId = uuid.v4();
    const {
      firstname,
      lastname,
      email,
      password,
      ahpra_num,
      provider_num,
      prescriber_num,
    } = req.body;

    if (await db.checkExistingUser(email)) {
      return res.status(400).json({
        errors: {
          values: email,
          msg: "cannot create user: given email already taken",
          param: "email",
          location: "body",
        },
      });
    }

    await aws.createNewRootFolder(userId);
    await db.insertNewUser(
      userId,
      firstname,
      lastname,
      email,
      password,
      ahpra_num,
      provider_num,
      prescriber_num
    );
    const accessToken = jwt.createWebToken({
      userid: userId,
      first_name: firstname,
      last_name: lastname,
      email: email,
      ahpra_num: ahpra_num,
      provider_num: provider_num,
      prescriber_num: prescriber_num,
    });

    return res.status(200).json({
      accessToken: accessToken,
    });
  } catch (err) {
    return res.status(500).json({ error: err });
  }
});

app.post("/user/login", validator.userValidation, async function (req, res) {
  db.pool.connect();

  const validationErrors = validationResult(req);
  if (!validationErrors.isEmpty()) {
    return res.status(400).json({ errors: validationErrors.array() });
  }
  const { email, password } = req.body;

  try {
    let loginOutcome = false;
    const validateUser = await db.checkLogin(email, password);
    let accessToken = undefined;

    if (validateUser) {
      loginOutcome = true;
      accessToken = jwt.createWebToken(validateUser);
    }
    return res.status(200).json({
      loginOutcome: loginOutcome,
      accessToken: accessToken,
      email: email,
      password: password,
    });
  } catch (err) {
    return res.status(500).json({ error: err });
  }
});

app.patch(
  "/user/:userId",
  validator.patchUserValidation,
  jwt.authenticateToken,
  async function (req, res) {
    db.pool.connect();

    const validationErrors = validationResult(req);
    if (!validationErrors.isEmpty()) {
      return res.status(400).json({ errors: validationErrors.array() });
    }
    const {
      firstname,
      lastname,
      email,
      password,
      ahpra_num,
      provider_num,
      prescriber_num,
    } = req.body;
    const userId = req.params.userId;
    let newAccesstoken = undefined;
    const prev = jwt.getTokenInfo(req);

    try {
      if (email && (await db.checkExistingUser(email))) {
        return res.status(400).json({
          errors: {
            values: email,
            msg: "cannot update user: given email already taken",
            param: "email",
            location: "body",
          },
        });
      }

      const updateOutcome = await db.updateUser(
        userId,
        firstname,
        lastname,
        email,
        password,
        ahpra_num,
        provider_num,
        prescriber_num
      );

      if (updateOutcome == true) {
        newAccesstoken = jwt.createWebToken({
          userid: userId,
          first_name: firstname === undefined ? prev.firstname : firstname,
          last_name: lastname === undefined ? prev.lastname : lastname,
          email: email === undefined ? prev.email : email,
          ahpra_num: ahpra_num === undefined ? prev.ahpra_num : ahpra_num,
          provider_num:
            provider_num === undefined ? prev.provider_num : provider_num,
          prescriber_num:
            prescriber_num === undefined ? prev.prescriber_num : prescriber_num,
        });
      }
      return res.status(200).json({
        updateOutcome: updateOutcome,
        newAccessToken: newAccesstoken,
      });
    } catch (err) {
      return res.status(500).json({ error: err });
    }
  }
);

app.delete(
  "/user/:userId",
  validator.deleteUserValidation,
  jwt.authenticateToken,
  async function (req, res) {
    db.pool.connect();

    const validationErrors = validationResult(req);
    if (!validationErrors.isEmpty()) {
      return res.status(400).json({ errors: validationErrors.array() });
    }
    const { userId } = req.params;

    try {
      await db.deleteUser(userId);
      await aws.deleteFolder(userId);

      return res.status(204).send();
    } catch (err) {
      return res.status(500).json({ error: err });
    }
  }
);

app.listen(3000, function () {
  console.log("App started");
});

// Export the app object. When executing the application local this does nothing. However,
// to port it to AWS Lambda we will create a wrapper around that will load the app from
// this file
module.exports = app;
