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
const jwt = require("./common/jwt");
const aws = require("./common/aws");
const multer = require("./common/multer");
const he = require("he");

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

app.get(
  "/file/list/:userId",
  validator.listFilesValidation,
  jwt.authenticateToken,
  async function (req, res) {
    const validationErrors = validationResult(req);
    if (!validationErrors.isEmpty()) {
      return res.status(400).json({ errors: validationErrors.array() });
    }
    const searchPath = req.query.path
      ? he.decode(`${req.params.userId}/${req.query.path}`)
      : `${req.params.userId}/`;

    try {
      return res.status(200).json({
        list: await aws.listFiles(searchPath),
      });
    } catch (err) {
      return res.status(500).json({ error: err });
    }
  }
);

app.post(
  "/file/upload",
  validator.uploadFileValidation,
  jwt.authenticateToken,
  multer.multerUploadFile.single("file"),
  async function (req, res) {
    const validationErrors = validationResult(req.headers);
    if (!validationErrors.isEmpty()) {
      return res.status(400).json({ errors: validationErrors.array() });
    }
    const uploadPath = req.headers["path"]
      ? `${req.headers["userid"]}/${req.headers["path"]}`
      : `${req.headers["userid"]}`;

    try {
      await aws.uploadFile(uploadPath, req.file);

      return res.status(204).send();
    } catch (err) {
      return res.status(500).json({ error: err });
    }
  }
);

app.post(
  "/file/folder",
  validator.createFolderValidation,
  jwt.authenticateToken,
  async function (req, res) {
    const validationErrors = validationResult(req);
    if (!validationErrors.isEmpty()) {
      return res.status(400).json({ errors: validationErrors.array() });
    }
    const { folderName } = req.body;
    const path = req.body.path
      ? he.decode(`${req.body.userId}/${req.body.path}`)
      : req.body.userId;

    try {
      await aws.createFolder(path, folderName);
      return res.status(204).send();
    } catch (err) {
      return res.status(500).json({ error: err });
    }
  }
);

app.delete(
  "/file/delete/:userId",
  validator.deleteFileValidation,
  jwt.authenticateToken,
  async function (req, res) {
    const validationErrors = validationResult(req);
    if (!validationErrors.isEmpty()) {
      return res.status(400).json({ errors: validationErrors.array() });
    }
    const path = he.decode(`${req.params.userId}/${req.query.path}`);

    try {
      await aws.deleteFile(path);
      return res.status(204).send();
    } catch (err) {
      return res.status(500).json({ error: err });
    }
  }
);

app.delete(
  "/file/delete-folder/:userId",
  validator.deleteFileValidation,
  jwt.authenticateToken,
  async function (req, res) {
    const validationErrors = validationResult(req);
    if (!validationErrors.isEmpty()) {
      return res.status(400).json({ errors: validationErrors.array() });
    }

    const path = he.decode(`${req.params.userId}/${req.query.path}`);

    try {
      await aws.deleteFolder(path);
      return res.status(204).send();
    } catch (err) {
      return res.status(500).json({ error: err });
    }
  }
);

app.post(
  "/file/move",
  validator.moveFileValidation,
  jwt.authenticateToken,
  async function (req, res) {
    const validationErrors = validationResult(req);
    if (!validationErrors.isEmpty()) {
      return res.status(400).json({ errors: validationErrors.array() });
    }
    const oldFileLocation = he.decode(
      `${req.body.userId}/${req.body.originalFile}`
    );
    const newFileLocation = req.body.newFileLocation
      ? he.decode(`${req.body.userId}/${req.body.newFileLocation}`)
      : he.decode(`${req.body.userId}/`);
    const fileArray = oldFileLocation.split("/");
    const fileName = fileArray[fileArray.length - 1];

    try {
      await aws.copyObject(oldFileLocation, newFileLocation, fileName);
      await aws.deleteFile(oldFileLocation);

      return res.status(204).send();
    } catch (err) {
      return res.status(500).json({ error: err });
    }
  }
);

app.post(
  "/file/rename-file",
  validator.renameFileValidation,
  jwt.authenticateToken,
  async function (req, res) {
    const validationErrors = validationResult(req);
    if (!validationErrors.isEmpty()) {
      return res.status(400).json({ errors: validationErrors.array() });
    }

    const oldFilePath = he.decode(req.body.oldFilePath);
    const newFileName = he.decode(req.body.newFileName);

    try {
      await aws.renameFile(oldFilePath, newFileName);
      await aws.deleteFolder(oldFilePath);

      return res.status(200).json({
        msg: `${oldFilePath} has been renamed to ${newFileName}`,
      });
    } catch (err) {
      return res.status(500).json({ error: err });
    }
  }
);

app.post(
  "/file/rename-folder",
  validator.renameFolderValidation,
  jwt.authenticateToken,
  async function (req, res) {
    const validationErrors = validationResult(req);
    if (!validationErrors.isEmpty()) {
      return res.status(400).json({ errors: validationErrors.array() });
    }

    const oldFolderPath = he.decode(req.body.oldFolderPath);
    const newFolderName = he.decode(req.body.newFolderName);

    try {
      await aws.renameFolder(oldFolderPath, newFolderName);
      await aws.deleteFolder(oldFolderPath);
      res.status(200).json({
        msg: `${oldFolderPath} has been renamed to ${newFolderName}`,
      });
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
