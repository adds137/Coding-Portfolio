const AWS = require("aws-sdk");
const he = require("he");

let s3 = new AWS.S3({
  region: "ap-southeast-2",
  accessKeyId: "AKIAZT43JW54NJMBOE6Z",
  secretAccessKey: "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",
});

const createNewRootFolder = async (uuid) => {
  try {
    const params = {
      Bucket: "petermac-bucket",
      Key: `${uuid}/`,
      Body: "",
    };

    await s3.upload(params).promise();
  } catch (error) {
    return new Error(`ERROR with creating root folder: ${err}`);
  }
};

const deleteFile = async (path) => {
  try {
    const params = {
      Bucket: "petermac-bucket",
      Key: path,
    };

    await s3.deleteObject(params).promise();
  } catch (error) {
    return new Error(`ERROR deleting a file: ${error}`);
  }
};

const deleteFolder = async (path) => {
  try {
    const listParams = {
      Bucket: "petermac-bucket",
      Prefix: path,
    };

    // Get all objects in the chosen file
    // (including all folder inside it and the chosen folder itself)
    const listedObjects = await s3.listObjectsV2(listParams).promise();
    // Create an array to separate file object and folder object
    let fileObjects = [];
    let folderObjects = [];

    // Separate file object and folder object
    listedObjects.Contents.forEach((obj) => {
      // If the last character in the key is "/", then it is a folder
      if (obj.Key.slice(-1) === "/") {
        folderObjects.push(obj);
      } else {
        fileObjects.push(obj);
      }
    });

    // Delete all file objects first
    // Iterate through all file objects and delete them
    for (let i = 0; i < fileObjects.length; i++) {
      await deleteFile(fileObjects[i].Key);
    }

    // Reverse folder list order, so we will delete the deepest folder first
    folderObjects.reverse();

    // Delete all folder objects last
    // Folder can only be deleted if it's empty
    for (let i = 0; i < folderObjects.length; i++) {
      await deleteFile(folderObjects[i].Key);
    }
  } catch (error) {
    return new Error(`Error deleting a folder: ${error}`);
  }
};

module.exports = { createNewRootFolder, deleteFolder };
