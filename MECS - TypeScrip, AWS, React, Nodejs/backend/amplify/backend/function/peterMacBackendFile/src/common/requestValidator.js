const { check } = require("express-validator");

const listFilesValidation = [
  check("userId")
    .exists()
    .withMessage("userId path is required")
    .trim()
    .escape()
    .custom((value) =>
      /^[0-9a-fA-F]{8}\b-[0-9a-fA-F]{4}\b-[0-9a-fA-F]{4}\b-[0-9a-fA-F]{4}\b-[0-9a-fA-F]{12}$/.test(
        value
      )
    )
    .withMessage("userId must be uuid format"),
  check("path")
    .optional({ nullable: true })
    .isString()
    .withMessage("path query must be string")
    .trim()
    .escape()
    .isLength({ min: 1 })
    .withMessage("path query cannot be blank"),
];

const uploadFileValidation = [
  check("userId")
    .exists()
    .withMessage("userId value is required")
    .trim()
    .escape()
    .custom((value) =>
      /^[0-9a-fA-F]{8}\b-[0-9a-fA-F]{4}\b-[0-9a-fA-F]{4}\b-[0-9a-fA-F]{4}\b-[0-9a-fA-F]{12}$/.test(
        value
      )
    )
    .withMessage("userId must be uuid format"),
  check("path")
    .optional({ nullable: true })
    .isString()
    .withMessage("path value must be string")
    .trim()
    .escape()
    .isLength({ min: 1 })
    .withMessage("path value cannot be blank"),
  check("file").exists().withMessage("requires file to upload"),
];

const createFolderValidation = [
  check("userId")
    .exists()
    .withMessage("userId is required")
    .trim()
    .escape()
    .custom((value) =>
      /^[0-9a-fA-F]{8}\b-[0-9a-fA-F]{4}\b-[0-9a-fA-F]{4}\b-[0-9a-fA-F]{4}\b-[0-9a-fA-F]{12}$/.test(
        value
      )
    )
    .withMessage("userId must be uuid format"),
  check("path")
    .optional({ nullable: true })
    .isString()
    .withMessage("path must be string")
    .trim()
    .escape()
    .isLength({ min: 1 })
    .withMessage("path cannot be blank"),
  check("folderName")
    .exists()
    .withMessage("folderName is required")
    .isString()
    .withMessage("folderName must be a string")
    .trim()
    .escape()
    .isLength({ min: 1 })
    .withMessage("folderName query cannot be blank"),
];

const deleteFileValidation = [
  check("userId")
    .exists()
    .withMessage("userId value is required")
    .trim()
    .escape()
    .custom((value) =>
      /^[0-9a-fA-F]{8}\b-[0-9a-fA-F]{4}\b-[0-9a-fA-F]{4}\b-[0-9a-fA-F]{4}\b-[0-9a-fA-F]{12}$/.test(
        value
      )
    )
    .withMessage("userId must be uuid format"),
  check("path")
    .exists()
    .withMessage("path value is required")
    .isString()
    .withMessage("path value must be string")
    .trim()
    .escape()
    .isLength({ min: 1 })
    .withMessage("path value cannot be blank"),
];

const moveFileValidation = [
  check("userId")
    .exists()
    .withMessage("userId value is required")
    .trim()
    .escape()
    .custom((value) =>
      /^[0-9a-fA-F]{8}\b-[0-9a-fA-F]{4}\b-[0-9a-fA-F]{4}\b-[0-9a-fA-F]{4}\b-[0-9a-fA-F]{12}$/.test(
        value
      )
    )
    .withMessage("userId must be uuid format"),
  check("originalFile")
    .exists()
    .withMessage("originalFile value is required")
    .isString()
    .withMessage("originalFile value must be string")
    .trim()
    .escape()
    .isLength({ min: 1 })
    .withMessage("originalFile value cannot be blank"),
  check("newFileLocation")
    .optional({ nullable: true })
    .isString()
    .withMessage("newFileLocation value must be string")
    .trim()
    .escape()
    .isLength({ min: 1 })
    .withMessage("newFileLocation value cannot be blank"),
];

const renameFileValidation = [
  check("userId")
    .exists()
    .withMessage("userId value is required")
    .trim()
    .escape()
    .custom((value) =>
      /^[0-9a-fA-F]{8}\b-[0-9a-fA-F]{4}\b-[0-9a-fA-F]{4}\b-[0-9a-fA-F]{4}\b-[0-9a-fA-F]{12}$/.test(
        value
      )
    )
    .withMessage("userId must be uuid format"),
  check("oldFilePath")
    .exists()
    .withMessage("oldFilePath value is required")
    .isString()
    .withMessage("oldFilePath value must be string")
    .trim()
    .escape()
    .isLength({ min: 1 })
    .withMessage("oldFilePath value cannot be blank"),
  check("newFileName")
    .exists()
    .withMessage("newFileName value is required")
    .isString()
    .withMessage("newFileName value must be string")
    .trim()
    .escape()
    .isLength({ min: 1 })
    .withMessage("newFileName value cannot be blank"),
];

const renameFolderValidation = [
  check("userId")
    .exists()
    .withMessage("userId value is required")
    .trim()
    .escape()
    .custom((value) =>
      /^[0-9a-fA-F]{8}\b-[0-9a-fA-F]{4}\b-[0-9a-fA-F]{4}\b-[0-9a-fA-F]{4}\b-[0-9a-fA-F]{12}$/.test(
        value
      )
    )
    .withMessage("userId must be uuid format"),
  check("oldFolderPath")
    .exists()
    .withMessage("oldFolderPath value is required")
    .isString()
    .withMessage("oldFolderPath value must be string")
    .trim()
    .escape()
    .isLength({ min: 1 })
    .withMessage("oldFolderPath value cannot be blank"),
  check("newFolderName")
    .exists()
    .withMessage("newFolderName value is required")
    .isString()
    .withMessage("newFolderName value must be string")
    .trim()
    .escape()
    .isLength({ min: 1 })
    .withMessage("newFolderName value cannot be blank"),
];

module.exports = {
  listFilesValidation,
  uploadFileValidation,
  createFolderValidation,
  deleteFileValidation,
  moveFileValidation,
  renameFileValidation,
  renameFolderValidation,
};
