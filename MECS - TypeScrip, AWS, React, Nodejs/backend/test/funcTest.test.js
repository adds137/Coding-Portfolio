const axios = require("axios");
const { deleteUser, getUserId } = require("./util/db");
const { deleteFolder, uploadFile } = require("./util/aws");

describe("func test", () => {
  jest.setTimeout(30000);

  const BASEURL =
    "https://9xgzyeg1c1.execute-api.ap-southeast-2.amazonaws.com/staging";
  const firstname = "testuser";
  const lastname = "funcTest";
  const folder1 = "test-folder1";
  let folder2 = "test-folder2";
  const file1 = "test-file.pdf";
  const file2 = "test-photo.JPG";
  const email = "testuser-functest@gmail.com.au";
  let password = "letmein1234";
  let accessToken;
  let userId;

  beforeAll(() => {
    deleteFolder(email);
    deleteUser(email);
  });

  it("FT001: Allow a new user to be registered into the system", async () => {
    const response = await axios.post(
      `${BASEURL}/User`,
      {
        firstname: firstname,
        lastname: lastname,
        email: email,
        password: "letmein1234",
        ahpra_num: "MED123",
        provider_num: "pid5678",
        prescriber_num: "2468",
      },
      { validateStatus: () => true }
    );

    console.log(response.data);
    expect(response.status).toBe(200);
  });

  it("FT002: Allow a existing user to login to the system", async () => {
    const response = await axios.post(
      `${BASEURL}/User/login`,
      {
        email: email,
        password: password,
      },
      { validateStatus: () => true }
    );

    console.log(response.data);
    accessToken = response.data.accessToken;
    expect(response.status).toBe(200);
    expect(response.data.loginOutcome).toBeTruthy();
  });

  it("FT003: Allow a user to update their details", async () => {
    userId = await getUserId(email);

    const response = await axios.patch(
      `${BASEURL}/User/${userId}`,
      {
        password: "updatedletmein1234",
        ahpra_num: "MED456",
        provider_num: "pid123",
        prescriber_num: "134679",
      },
      {
        headers: { Authorization: `Bearer ${accessToken}` },
        validateStatus: () => true,
      }
    );

    console.log(response.data);
    accessToken = response.data.newAccessToken;
    expect(response.status).toBe(200);
    expect(response.data.updateOutcome).toBeTruthy();
  });

  it("FT004: Allow users to create 2 folders", async () => {
    const response1 = await axios.post(
      `${BASEURL}/File/folder`,
      {
        userId: userId,
        folderName: folder1,
      },
      {
        headers: { Authorization: `Bearer ${accessToken}` },
        validateStatus: () => true,
      }
    );

    expect(response1.status).toBe(204);

    const response2 = await axios.post(
      `${BASEURL}/File/folder`,
      {
        userId: userId,
        path: folder1,
        folderName: folder2,
      },
      {
        headers: { Authorization: `Bearer ${accessToken}` },
        validateStatus: () => true,
      }
    );

    expect(response2.status).toBe(204);
  });

  it("FT005: Allow user to move a file from one location to another ", async () => {
    // upload files for testing, function done on front end
    await uploadFile(userId, file1, "test/testFile/test-file.pdf");
    await uploadFile(userId, file2, "test/testFile/test-photo.JPG");

    const response = await axios.post(
      `${BASEURL}/File/move`,
      {
        userId: userId,
        originalFile: file1,
        newFileLocation: `${folder1}/`,
      },
      {
        headers: { Authorization: `Bearer ${accessToken}` },
        validateStatus: () => true,
      }
    );

    expect(response.status).toBe(204);
  });

  it("FT006: Allow a user to rename a file", async () => {
    const response = await axios.post(
      `${BASEURL}/File/rename-file`,
      {
        userId: userId,
        oldFilePath: `${userId}/${file2}`,
        newFileName: `updated${file2}`,
      },
      {
        headers: { Authorization: `Bearer ${accessToken}` },
        validateStatus: () => true,
      }
    );

    console.log(response.data);
    expect(response.status).toBe(200);
    expect(response.data.msg).toBe(
      `${userId}/${file2} has been renamed to updatedtest-photo.JPG`
    );
  });

  it("FT007: Allow a user to rename a folder", async () => {
    const response = await axios.post(
      `${BASEURL}/File/rename-folder`,
      {
        userId: userId,
        oldFolderPath: `${userId}/${folder1}/${folder2}`,
        newFolderName: `updated${folder2}`,
      },
      {
        headers: { Authorization: `Bearer ${accessToken}` },
        validateStatus: () => true,
      }
    );

    console.log(response.data);
    expect(response.status).toBe(200);
    folder2 = `updated${folder2}`;
    expect(response.data.msg).toBe(
      `${userId}/${folder1}/test-folder2 has been renamed to updatedtest-folder2`
    );
  });

  it("FT008: Allow a user to list files and folders at root", async () => {
    const response = await axios.get(`${BASEURL}/File/list/${userId}`, {
      headers: { Authorization: `Bearer ${accessToken}` },
      validateStatus: () => true,
    });

    console.log(response.data);
    expect(response.status).toBe(200);
    expect(response.data.list.length).toEqual(4);
  });

  it("FT009: Allow a user to send an their certificate as an email", async () => {
    const response = await axios.post(
      `${BASEURL}/email`,
      {
        userId: userId,
        toEmail: "hospital@hotmail.com",
        toName: " peterMac-hospital",
        fromEmail: email,
        fromName: `${firstname}-${lastname}`,
        message: "test message",
        certificate: [
          {
            name: "test-cert-name.pdf",
            link: "https://www.rmit.edu.au/",
          },
        ],
      },
      {
        headers: { Authorization: `Bearer ${accessToken}` },
        validateStatus: () => true,
      }
    );

    expect(response.status).toBe(204);
  });

  it("FT010: Allow a user to delete a file", async () => {
    const response = await axios.delete(
      `${BASEURL}/File/delete/${userId}?path=${folder1}/${file1}`,
      {
        headers: { Authorization: `Bearer ${accessToken}` },
        validateStatus: () => true,
      }
    );

    expect(response.status).toBe(204);
  });

  it("FT011: Allow a user to delete a folder", async () => {
    const response = await axios.delete(
      `${BASEURL}/File/delete-folder/${userId}?path=${folder1}/${folder2}/`,
      {
        headers: { Authorization: `Bearer ${accessToken}` },
        validateStatus: () => true,
      }
    );

    expect(response.status).toBe(204);
  });

  it("FT012: Allow a user to delete their account", async () => {
    const response = await axios.delete(`${BASEURL}/User/${userId}`, {
      headers: { Authorization: `Bearer ${accessToken}` },
      validateStatus: () => true,
    });

    expect(response.status).toBe(204);
  });
});
