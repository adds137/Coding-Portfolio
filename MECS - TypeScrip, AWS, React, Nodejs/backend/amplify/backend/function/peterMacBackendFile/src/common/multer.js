const multer = require("multer");

const uploadFileStorage = multer.memoryStorage();

const uploadFileFilter = (req, file, cb) => {
  if (
    file.mimetype.split("/")[0] === "image" &&
    file.mimetype === "application/pdf"
  ) {
    cb(null, true);
  } else {
    cb(new multer.MulterError("LIMIT_UNEXPECTED_FILE"), false);
  }
};

const multerUploadFile = multer({
  uploadFileStorage,
  uploadFileFilter,
});

module.exports = { multerUploadFile };
