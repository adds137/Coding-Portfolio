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

const INSERT_USER_QUERY = `INSERT INTO petermacdb.users (userID, first_name, last_name, email, password, ahpra_num, provider_num, prescriber_num)
VALUES ($1, $2, $3, $4, $5, $6, $7, $8)`;

const CHECK_EXISTING_USER_QUERY =
  "SELECT * from petermacdb.users WHERE email = $1";

const CHECK_LOGIN_QUERY =
  "SELECT userID, first_name, last_name, email, ahpra_num, provider_num, prescriber_num from petermacdb.users WHERE email = $1 AND password = $2 LIMIT 1";

const DELETE_USER_QUERY = `DELETE FROM petermacdb.users WHERE userID = $1`;

const insertNewUser = async (
  userId,
  firstname,
  lastname,
  email,
  password,
  ahpra_num,
  provider_num,
  prescriber_num
) => {
  try {
    const result = await pool.query(INSERT_USER_QUERY, [
      userId,
      firstname,
      lastname,
      email,
      password,
      ahpra_num,
      provider_num,
      prescriber_num,
    ]);
    return result;
  } catch (error) {
    return new Error(`ERROR with db inserting user: ${err}`);
  }
};

const updateUser = async (userId, firstname, lastname, email, password, ahpra_num, provider_num, prescriber_num) => {
  try {
    const updateUserQuery = createUpdateQuery(
      firstname,
      lastname,
      email,
      password,
      ahpra_num,
      provider_num,
      prescriber_num
    );

    const result = await pool.query(updateUserQuery, [userId]);
    return result.rowCount == 1 ? true : false;
  } catch (err) {
    return new Error(`ERROR with db updating user details: ${err}`);
  }
};

const createUpdateQuery = (
  firstname,
  lastname,
  email,
  password,
  ahpra_num,
  provider_num,
  prescriber_num
) => {
  let update_query = "UPDATE petermacdb.users SET";

  if (email) {
    update_query = update_query.concat(" ", `email = '${email}',`);
  }
  if (password) {
    update_query = update_query.concat(" ", `password = '${password}',`);
  }
  if (firstname) {
    update_query = update_query.concat(" ", `first_name = '${firstname}',`);
  }
  if (lastname) {
    update_query = update_query.concat(" ", `last_name = '${lastname}',`);
  }
  if (ahpra_num) {
    update_query = update_query.concat(" ", `ahpra_num = '${ahpra_num}',`);
  }
  if (provider_num) {
    update_query = update_query.concat(
      " ",
      `provider_num = '${provider_num}',`
    );
  }
  if (prescriber_num) {
    update_query = update_query.concat(
      " ",
      `prescriber_num = '${prescriber_num}',`
    );
  }
  return update_query.concat(" ", "update_date = now() WHERE userid = $1");
};

const checkExistingUser = async (email) => {
  try {
    const result = await pool.query(CHECK_EXISTING_USER_QUERY, [email]);

    return result.rows.length != 0;
  } catch (error) {
    return new Error(`ERROR with db checking existing user: ${err}`);
  }
};

const checkLogin = async (email, password) => {
  try {
    const result = await pool.query(CHECK_LOGIN_QUERY, [email, password]);
    const validUser = result.rows[0] ? result.rows[0] : undefined;

    return validUser;
  } catch (error) {
    return new Error(`ERROR with db checking user credential: ${err}`);
  }
};

const deleteUser = async (id) => {
  try {
    const result = await pool.query(DELETE_USER_QUERY, [id]);

    return result.rows.length != 0;
  } catch (error) {
    return new Error(`ERROR with db deleting user: ${err}`);
  }
};

module.exports = {
  pool,
  insertNewUser,
  checkExistingUser,
  checkLogin,
  updateUser,
  deleteUser,
};
