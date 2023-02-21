const { Pool } = require("pg");

const pool = new Pool({
  user: "peterMacAdmin",
  host: "petermac-database.c7lm1ctx7zlz.ap-southeast-2.rds.amazonaws.com",
  database: "peterMacDb",
  password: "yu&hlsweKL2#l_&fRuH+",
  port: 5432,
});

pool.on("error", (err, client) => {
  return new Error(`ERROR with db connection: ${err}`);
});

const DELETE_USER_QUERY = `DELETE FROM petermacdb.users WHERE email = $1`;

const GET_USERID_QUERY = "SELECT userid FROM petermacdb.users WHERE email = $1";

const deleteUser = async (email) => {
  try {
    await pool.query(DELETE_USER_QUERY, [email]);
  } catch (error) {
    return new Error(`ERROR with db deleting user: ${err}`);
  }
};

const getUserId = async (email) => {
  try {
    const result = await pool.query(GET_USERID_QUERY, [email]);

    return result.rows[0].userid;
  } catch (error) {
    return new Error(`ERROR with db getting userid: ${error}`);
  }
};

module.exports = { deleteUser, getUserId };
