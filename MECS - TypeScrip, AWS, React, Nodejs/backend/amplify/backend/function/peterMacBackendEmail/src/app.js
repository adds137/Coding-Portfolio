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
const jwt = require("./common/jwt");
const { validationResult } = require("express-validator");
const nodemailer = require("nodemailer");
const email = require("./common/emailTemplate");

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

app.post(
  "/email",
  validator.postEmailValidation,
  jwt.authenticateToken,
  async function (req, res) {
    const validationErrors = validationResult(req);
    if (!validationErrors.isEmpty()) {
      return res.status(400).json({ errors: validationErrors.array() });
    }

    const middleEmail = "appmecs@gmail.com";
    const transporter = nodemailer.createTransport({
      service: "gmail",
      auth: {
        user: middleEmail,
        pass: "rmkc jczz dtjv dbki",
      },
    });
    const { toName, toEmail, fromName, fromEmail, message, certificate } =
      req.body;

    try {
      const emailBody = email.createTemplate(
        toName,
        fromName,
        fromEmail,
        message,
        certificate
      );

      const mailDetails = {
        from: middleEmail,
        to: toEmail,
        cc: fromEmail,
        subject: "Certificate Transfer using MECS",
        html: emailBody,
      };

      await transporter.sendMail(mailDetails);

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
