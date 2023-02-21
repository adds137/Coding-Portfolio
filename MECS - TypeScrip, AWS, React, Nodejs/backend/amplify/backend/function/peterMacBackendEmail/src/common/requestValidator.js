const { check } = require("express-validator");

const postEmailValidation = [
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
  check("toEmail")
    .exists()
    .withMessage("No value given for toEmail field")
    .isString()
    .withMessage("Invalid type for toEmail field")
    .notEmpty()
    .withMessage("toEmail field cannot be blank")
    .trim()
    .escape()
    .custom((value) => !/\s/.test(value))
    .withMessage("No spaces are allowed in the toEmail field")
    .isEmail()
    .normalizeEmail()
    .withMessage("Must be a valid email address"),
  check("toName")
    .exists()
    .withMessage("No value given for toName field")
    .isString()
    .withMessage("Invalid type for toName field")
    .notEmpty()
    .withMessage("toName field cannot be blank")
    .trim()
    .escape()
    .custom((value) => !/\s/.test(value))
    .withMessage("No spaces are allowed in the toName field"),
  check("fromEmail")
    .exists()
    .withMessage("No value given for fromEmail field")
    .isString()
    .withMessage("Invalid type for fromEmail field")
    .notEmpty()
    .withMessage("fromEmail field cannot be blank")
    .trim()
    .escape()
    .custom((value) => !/\s/.test(value))
    .withMessage("No spaces are allowed in the fromEmail field")
    .isEmail()
    .normalizeEmail()
    .withMessage("Must be a valid email address"),
  check("fromName")
    .exists()
    .withMessage("No value given for toName field")
    .isString()
    .withMessage("Invalid type for toName field")
    .notEmpty()
    .withMessage("toName field cannot be blank")
    .trim()
    .escape()
    .custom((value) => !/\s/.test(value))
    .withMessage("No spaces are allowed in the toName field"),
  check("message")
    .exists()
    .withMessage("No value given for message field")
    .isString()
    .withMessage("Invalid type for message field")
    .notEmpty()
    .withMessage("message field cannot be blank")
    .trim()
    .escape(),
  check("certificate")
    .exists()
    .withMessage("No value given for certificate field")
    .isArray({ min: 1 })
    .withMessage("certificate field must be array with at least 1 value"),
  check("certificate.*.name")
    .exists()
    .withMessage("No value given for certificate.*.name field")
    .isString()
    .withMessage("Invalid type for certificate.*.name field")
    .notEmpty()
    .withMessage("certificate.*.name field cannot be blank")
    .trim()
    .escape(),
  check("certificate.*.link")
    .exists()
    .withMessage("No value given for certificate.*.link field")
    .isString()
    .withMessage("Invalid type for certificate.*.link field")
    .notEmpty()
    .withMessage("certificate.*.link field cannot be blank")
    .trim()
    .escape(),
];

module.exports = {
  postEmailValidation,
};
