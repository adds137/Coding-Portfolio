const AWS = require("aws-sdk");
const he = require("he");

let s3 = new AWS.S3({
  region: "ap-southeast-2",
  accessKeyId: "AKIAZT43JW54NJMBOE6Z",
  secretAccessKey: "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",
});

const listFiles = async (path) => {
  const params = {
    Bucket: "petermac-bucket",
    Prefix: path,
  };

  try {
    const fileList = s3.listObjectsV2(params).promise();
    const fileArray = [];

    for (let i = 1; i < (await fileList).Contents.length; i++) {
      let objectName;
      let objectType;
      let objectFileType;

      if ((await fileList).Contents[i].Key.slice(-1) == "/") {
        objectName = (await fileList).Contents[i].Key.split("/").slice(-2)[0];
        objectType = "folder";
        objectFileType = undefined;
      } else {
        objectName = (await fileList).Contents[i].Key.split("/").slice(-1)[0];
        objectType = "file";
        objectFileType = (await fileList).Contents[i].Key.split(".").slice(
          -1
        )[0];
      }

      fileArray.push({
        name: objectName,
        objectType: objectType,
        fileType: objectFileType,
        key: (await fileList).Contents[i].Key,
        lastModified: (await fileList).Contents[i].LastModified,
        eTag: (await fileList).Contents[i].ETag,
        url:
          objectType == "file"
            ? await getUrl((await fileList).Contents[i].Key)
            : undefined,
      });
    }

    return fileArray;
  } catch (error) {
    return new Error(`ERROR with aws listFiles: ${err}`);
  }
};

const uploadFile = async (path, file) => {
  const params = {
    Bucket: "petermac-bucket",
    Key: `${path}/${file.originalname}`,
    Body: file.buffer,
    ContentType: file.mimetype,
  };

  try {
    await s3.upload(params).promise();
  } catch (err) {
    return new Error(`ERROR with aws uploadFile: ${err}`);
  }
};

const getUrl = async (key) => {
  const params = {
    Bucket: "petermac-bucket",
    Key: key,
    Expires: 604800,
  };

  try {
    return await s3.getSignedUrl("getObject", params);
  } catch (err) {
    return new Error(`ERROR with aws uploadFile: ${err}`);
  }
};

const createFolder = async (path, folderName) => {
  try {
    const params = {
      Bucket: "petermac-bucket",
      Key: `${path}/${folderName}/`,
      Body: "",
    };

    await s3.upload(params).promise();
  } catch (error) {
    return new Error(`ERROR with creating folder: ${err}`);
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

const copyObject = async (oldFile, newFile, fileName) => {
  try {
    const moveParams = {
      Bucket: "petermac-bucket",
      CopySource: `petermac-bucket/${oldFile}`,
      Key: `${newFile}${fileName}`,
    };

    await s3.copyObject(moveParams).promise();
  } catch (err) {
    return new Error(`Error in moving file: ${err}`);
  }
};

const renameFile = async (oldFilePath, newFileName) => {
  try {
    // Convert path into array by spliiting the "/"
    const pathArr = oldFilePath.split("/");

    // Remove the old file name, because I want to get the root path 1 directory above
    // E.g, USERID/root/oldfile  === after pop ==>   USERID/root
    pathArr.pop();

    const renameParams = {
      Bucket: "petermac-bucket",
      CopySource: `petermac-bucket/${oldFilePath}`,
      Key: `${pathArr.join("/")}/${newFileName}`,
    };

    await s3.copyObject(renameParams).promise();
  } catch (err) {
    return new Error(`Error in renaming file: ${err}`);
  }
};

const renameFolder = async (oldFolderPath, newFolderName) => {
  try {
    const listParams = {
      Bucket: "petermac-bucket",
      Prefix: oldFolderPath,
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
        folderObjects.push(obj.Key.substring(0, obj.Key.length - 1).split("/"));
      } else {
        fileObjects.push(obj.Key.split("/"));
      }
    });

    // Find the index of the folder that we want to rename
    // It will always be the length of object in index 0,
    // because we list all objects starting from that old folder
    const oldFolderIndex = folderObjects[0].length;

    // Remove the old folder name, because I want to get the root path 1 directory above
    // E.g, USERID/root/oldfolder  === after pop ==>   USERID/root
    folderObjects[0].pop();

    // Create new root folder for the rename using the root path 1 directory above

    await createFolder(folderObjects[0].join("/"), newFolderName);

    // Loop through all folderObjects and create the new folder in the new path
    for (let i = 1; i < folderObjects.length; i++) {
      folderObjects[i][oldFolderIndex - 1] = newFolderName;
      newRootPath = folderObjects[i].splice(0, oldFolderIndex).join("/");
      newFolderPath = folderObjects[i].join("/");
      await createFolder(newRootPath, newFolderPath);
    }

    // Loop through all fileObjects and copy all those files into the new folder path

    for (let i = 0; i < fileObjects.length; i++) {
      oldRootPath = fileObjects[i].join("/");
      fileObjects[i][oldFolderIndex - 1] = newFolderName;
      newRootPath = fileObjects[i].join("/");

      renameParams = {
        Bucket: "petermac-bucket",
        CopySource: `petermac-bucket/${oldRootPath}`,
        Key: `${newRootPath}`,
      };

      await s3.copyObject(renameParams).promise();
    }

    return "";
  } catch (err) {
    return new Error(`Error in renaming folder: ${err}`);
  }
};

module.exports = {
  listFiles,
  uploadFile,
  createFolder,
  deleteFile,
  deleteFolder,
  copyObject,
  renameFile,
  renameFolder,
};
