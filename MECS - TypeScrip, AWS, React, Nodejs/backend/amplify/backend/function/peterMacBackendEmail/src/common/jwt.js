const jwt = require("jsonwebtoken");

const SECRET_ACCESS_TOKEN =
  "307ad203d1df1d234139e0b503b850fc8b8b98cf1b630075f1a1b318c03356b39bbcb584c9ca1b7a3967f96f3a0baa61ff2d759e609b709de0a503049e8466e3";

const authenticateToken = (req, res, next) => {
  const authHeader = req.headers["authorization"];
  const accessToken = authHeader && authHeader.split(" ")[1];

  if (accessToken == null) {
    return res.status(401).json({ errors: "missing or invalid credentials" });
  }

  jwt.verify(accessToken, SECRET_ACCESS_TOKEN, (err, user) => {
    if (err) {
      return res.status(500).json({ error: err });
    }

    if (
      req.params.userId !== user.userid &&
      req.headers["userid"] !== user.userid &&
      req.body.userId !== user.userid
    ) {
      return res.status(403).json({ errors: "missing or invalid credentials" });
    }

    req.user = user;
    next();
  });
};

module.exports = { authenticateToken };
