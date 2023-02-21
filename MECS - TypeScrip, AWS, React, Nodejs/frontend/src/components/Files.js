import React, { Component } from "react";
import "../css/Files.css";
import uploadicon from "../images/upload-icon.png";
import jwt_decode from "jwt-decode";
import { Navigate } from "react-router-dom";
import S3FileUpload from "react-s3";
import axios from "axios";
import SuccessModal from "./Modal/SuccessModal";
import MoveSuccess from "./Modal/MoveSuccess";
import authHeader from "../services/auth-header";
import AuthService from "../services/auth.service";
import close_icon from '../images/close_icon.png';

window.Buffer = window.Buffer || require("buffer").Buffer;

const apiLink =
  "https://9xgzyeg1c1.execute-api.ap-southeast-2.amazonaws.com/staging/";

// Set up config for individual user's S3 database
const config = {
  bucketName: "petermac-bucket",
  dirName: "",
  region: "ap-southeast-2",
  accessKeyId: "AKIAZT43JW54NJMBOE6Z",
  secretAccessKey: "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",
};

class Files extends Component {
  constructor(props) {
    super(props);
    this.state = {
      files: [],
      folderList: [],
      currentPath: "",
      chosenFile: undefined,
      userid: "",
      folderName: "",
      uploadSuccess: false,
      moveSuccess: false,
      isSubmit: false,
      certificates: [],
      emailRecipient: "",
      emailSender: "",
      recipientName: "",
      senderName: "",
      message: "",
      certMsg: "",
      tokenExpired: false,
      showMoveFileDialog: false,
      selectedFolder: '',
      selectedFile: ''
    };
  }

  componentDidMount() {
    const accessToken = localStorage.getItem("user");
    if (accessToken !== null) {
      const data = jwt_decode(accessToken);
      this.setState(
        {
          userid: data.userid,
          emailSender: data.email,
          senderName: data.first_name,
        },
        () => this.setPath(data.userid)
      );
    }
  }

  setPath(path) {
    path = path.replace(/\//, "?path=");
    if (path.slice(-1) === "/") {
      path = path.slice(0, -1);
    }
    this.updateFiles(path);
  }

  updateFiles(path) {
    const api = apiLink + "File/list/" + path;
    fetch(api, authHeader())
      .then((res) => res.json())
      .then(
        (files) => {
          console.log(files);
          console.log(path);
          if (files.error) {
            if (files.error.name === "TokenExpiredError") {
              AuthService.logout();
              this.setState({ tokenExpired: true });
              alert("Please login to view your files");
              return;
            }
          }
          files = this.sortFiles(files);
          this.setState({ files: files.list });
          this.setState({ currentPath: path });
          // Set folder list to root file path (for displaying folders in move file)
          if (path === this.state.userid) {
            this.setState({folderList: files.list})
          }
        },
        (error) => {
          console.log(error);
        }
      );
  }

  // Re order file list to display folders first then files
  sortFiles = (files) => {
    var list = [];
    // add folders to list
    files.list.forEach((file) => {
      if (!file.url) {
        list.push(file);
      }
    });
    // then add files
    files.list.forEach((file) => {
      if (file.url) {
        list.push(file);
      }
    });
    return { list };
  };

  onChange = (e) => {
    this.setState({
      chosenFile: e.target.files[0],
    });
  };

  handleChange = (e) => {
    this.setState({
      [e.target.id]: e.target.value,
    });
  };

  closeModal = () => {
    this.setState({
      uploadSuccess: false,
      moveSuccess: false
    });
  };

  // Upload files to user's database
  handleUpload = (e) => {
    e.preventDefault();
    let myFile = this.state.chosenFile;

    // Set dirName for S3 config
    // set dirName is placed here, so files will be uploaded
    // based on the current folder/path
    let path = this.state.currentPath.replace("?path=", "/");

    config.dirName = path;

    this.setState({
      isSubmit: true,
    });

    if (myFile === undefined) {
      // No file chosen
      console.log("No file chosen!");
    } else {
      // If user has chosen a file, then upload it to S3
      console.log(this.state.chosenFile);
      this.setState({
        isSubmit: false,
      });
      S3FileUpload.uploadFile(myFile, config)
        // Refresh the table after upload has been completed
        .then(() => {
          this.setPath(path);
          this.setState(
            {
              uploadSuccess: true,
            },
            () => {
              setTimeout(this.closeModal, 1000);
            }
          );
        })
        .catch((err) => console.error(err));
    }
    e.target.reset();
  };

  // check if file/folder is not in a folder
  isNotInSubfolder(file) {
    const path = this.state.currentPath.replace("?path=", "/");
    var match = path + "/" + file.name;
    if (file.objectType === "folder") {
      match += "/";
    }
    if (file.key === match) {
      return true;
    }
    return false;
  }

  toggleCheckbox = (file) => {
    // Check if certificate is selected/checked
    const certChecked = this.state.certificates.some(e => e.key === file.key);

    if (!certChecked) {
      this.selectCertificate(file)
    } else {
      this.deselectCertificate(file)
    }
  }

  // Select cert and add to list of certs selected
  selectCertificate = (file) => {
    var newCerts = {};
    var oldCerts = this.state.certificates;
    file.link = file.url;
    oldCerts.push(file)
    newCerts.list = oldCerts

    this.setState({certificates: newCerts.list})
  };

  // Deselect cert and remove from list of certs selected
  deselectCertificate = (file) => {
    var newCerts = {};
    var oldCerts = this.state.certificates;
    
    var filtered = oldCerts.filter(function(cert) { return cert.name !== file.name; });
    newCerts.list = filtered;

    this.setState({certificates: newCerts.list})
  };

  sendCertificate = (e) => {
    e.preventDefault();
    const userId = this.state.userid;
    const toEmail = this.state.emailRecipient;
    const toName = this.state.recipientName;
    const fromEmail = this.state.emailSender;
    const fromName = this.state.senderName;
    const message = this.state.message;
    const certificate = this.state.certificates;
    this.setState({
      certMsg: <div className="text-green-500">Please wait...</div>,
    });
    axios
      .post(
        apiLink + "email",
        {
          userId,
          toEmail,
          toName,
          fromEmail,
          fromName,
          message,
          certificate,
        },
        authHeader()
      )
      .then((response) => {
        console.log(response.data);
        this.setState({
          certMsg: <div className="text-green-500">Email Sent!</div>,
        });
      })
      .catch((error) => {
        console.log(error);
        if (error.response.data.errors[0]) {
          this.setState({
            certMsg: error.response.data.errors[0].msg,
          });
        } else {
          this.setState({
            certMsg: "There was an error sending the certificate",
          });
        }
        if (this.state.certificates.length === 0) {
          this.setState({ certMsg: "Please select a certificate to send" });
        }
      });
  };

  deleteFile = (file) => {
    if (window.confirm("Are you sure you want to delete this file?")) {
      const fileName = file.name;
      var path = this.state.currentPath;

      // Add api link with ?path={fileName} at the end of the link
      if (path.includes("?path=")) {
        path += `/${fileName}`;
      } else {
        path += `?path=${fileName}`;
      }

      const api = `${apiLink}File/delete/${path}`;

      // Call api endpoint for delete file
      axios
        .delete(api, authHeader())
        .then((response) => {
          console.log("Delete File Successful!");
          // Call setPath to update the table
          path = this.state.currentPath.replace("?path=", "/");
          this.setPath(path);
        })
        .catch((error) => {
          console.log(error);
        });
    }
  };

  deleteFolder = (folder) => {
    if (window.confirm("Are you sure you want to delete this folder?")) {
      const folderName = folder.name;
      var path = this.state.currentPath;

      // Add api link with ?path={fileName} at the end of the link
      if (path.includes("?path=")) {
        path += `/${folderName}`;
      } else {
        path += `?path=${folderName}`;
      }

      const api = `${apiLink}File/delete-folder/${path}/`;

      // Call api endpoint for delete folder
      axios
        .delete(api, authHeader())
        .then((response) => {
          // Call setPath to update the table
          path = this.state.currentPath.replace("?path=", "/");
          this.setPath(path);
          console.log("Delete Folder Successful!");
        })
        .catch((error) => {
          console.log(error);
        });
    }
  };

  selectFolder = (folder) => {
    this.setState({selectedFolder: folder})
  }

  selectFile = (file) => {
    this.setState({selectedFile: file})
  }

  renameFile = (file) => {
    const fileType = file.name.slice(file.name.lastIndexOf('.'))

    const newName = prompt("Enter new name for file: " + file.name.slice(0, file.name.lastIndexOf('.')))
    var params = {
      userId: this.state.userid,
      oldFilePath: file.key,
      newFileName: newName+fileType,
    }
    console.log(params)
    
    if (newName !== null) {
      if (newName !== "") {
        axios
          .post(apiLink + "File/rename-file", params, authHeader())
          .then((response) => {
            console.log(response)
            this.setPath(this.state.currentPath);
          })
          .catch((error) => {
            console.log(error);
          });
      } else {
        alert("File name cannot be empty!")
      }
    }
  }

  renameFolder = (file) => {
    const newName = prompt("Enter new name for folder: " + file.name)
    var params = {
      userId: this.state.userid,
      oldFolderPath: file.key,
      newFolderName: newName,
    }
    console.log(params)
    
    if (newName !== null) {
      if (newName !== "") {
        axios
          .post(apiLink + "File/rename-folder", params, authHeader())
          .then((response) => {
            console.log(response)
            this.setPath(this.state.currentPath);
          })
          .catch((error) => {
            console.log(error);
          });
      } else {
        alert("Folder name cannot be empty!")
      }
    }
  }

  render() {
    if (localStorage.getItem("user") === null || this.state.tokenExpired) {
      return <Navigate to="/login" />;
    }

    const backButton = () => {
      var backButton = "";
      var path = this.state.currentPath;
      var previousPath = "";
      if (path.includes("/")) {
        previousPath = path
          .slice(0, path.lastIndexOf("/"))
          .replace("?path=", "/");
        backButton = (
          <tr>
            <td></td>
            <td>
              <u>
                <button onClick={() => this.setPath(previousPath)}>
                  Back <i className="fa fa-level-up" />
                </button>
              </u>
            </td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
          </tr>
        );
      } else if (path.includes("?path=")) {
        previousPath = path.slice(0, path.indexOf("?path="));
        backButton = (
          <tr>
            <td></td>
            <td>
              <u>
                <button onClick={() => this.setPath(previousPath)}>
                  Back <i className="fa fa-level-up" />
                </button>
              </u>
            </td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
          </tr>
        );
      } else {
        return backButton;
      }
      return backButton;
    };

    const createFolder = (e) => {
      e.preventDefault();
      const userId = this.state.userid;
      const folderName = this.state.folderName;
      const api = apiLink + "File/folder";
      var path = this.state.currentPath;
      var params = {};

      if (path.includes("?path=")) {
        path = path.slice(path.indexOf("=") + 1, path.length);
        params = { userId, path, folderName };
      } else {
        params = { userId, folderName };
      }

      axios
        .post(api, params, authHeader())
        .then((response) => {
          path = this.state.currentPath.replace("?path=", "/");
          this.setPath(path);
        })
        .catch((error) => {
          console.log(error);
        });
      e.target.reset();
    };

    const fileIcon = (fileType) => {
      switch (fileType) {
        case "txt":
          return <i className="fa fa-file-text-o fa-2x" />;
        case "pdf":
          return <i className="fa fa-file-pdf-o fa-2x" />;
        case "docx":
          return <i className="fa fa-file-word-o fa-2x" />;
        case "jpg":
        case "png":
          return <i className="fa fa-file-image-o fa-2x" />;
        default:
          return fileType;
      }
    };

    const modifiedDate = (date) => {
      let dateTimeUTC = new Date(date);
      var day = dateTimeUTC.getDate();
      var month = dateTimeUTC.getMonth() + 1;
      var year = dateTimeUTC.getFullYear();
      var time = dateTimeUTC.toLocaleString("en-US", {
        hour: "numeric",
        minute: "numeric",
        hour12: true,
      });

      date = day + "/" + month + "/" + year + " " + time;
      return date.toString();
    };

    const showFile = (file) => {
      return (
        <tr id={file.key} key={file.key}>
          {/* File/Folder Icon */}
          <td>
            {file.url ? (
              fileIcon(file.fileType)
            ) : (
              <i className="fa fa-folder-o fa-2x" />
            )}
          </td>
          {/* File/Folder Name */}
          <td>
            <u>
              {file.url ? (
                <a href={file.url}>{file.name}</a>
              ) : (
                <button onClick={() => this.setPath(file.key)}>
                  {file.name}
                </button>
              )}
            </u>
          </td>
          {/* Rename File/Folder */}
          <td>
            <button onClick={file.url ? ()=>this.renameFile(file) : ()=>this.renameFolder(file)}><i className="fa fa-pencil-square-o fa-2x" /></button>
          </td>
          {/* Date Modified */}
          <td>{file.url ? modifiedDate(file.lastModified) : "--"}</td>
          {/* Select Certificate */}
          <td>
            {file.url ? (
              <input className="w-7 h-7" type="checkbox" id={file.key} key={file.key} onClick={()=>this.toggleCheckbox(file)} />
            ) : "--"}
          </td>
          {/* Move File */}
          <td>
            {file.url ? (
            <button onClick={()=>handleMoveFile(file)}>
              <i className="fa fa-arrows fa-2x" />
            </button>
            ) : "--"}
          </td>
          {/* Delete File/Folder */}
          <td>
            <button
              onClick={() => {
                file.url ? this.deleteFile(file) : this.deleteFolder(file);
              }}
              className="bg-rose-600 hover:bg-rose-700 text-white font-bol py-2 px-2 rounded-lg"
            >
              <i className="fa fa-trash-o fa-2x" />
            </button>
          </td>
        </tr>
      );
    };

    const handleMoveFile = (file) => {
      setShowMoveFileDialog(true)
      this.selectFile(file)
    }

    const setShowMoveFileDialog = (bool) => {
      this.setState({showMoveFileDialog:bool})
    }

    function MoveFile({ show, setShow }) {
      return (
        <>
          {show && 
            <div className="fixed top-0 left-0 right-0 bottom-0 flex items-center justify-center p-10 bg-black bg-opacity-25 z-40">
              <div className='bg-white p-10 rounded-lg max-w-2xl z-50 relative'>
                <div 
                  onClick={() => setShow(false)}
                  className='absolute top-3 right-3 bg-gray-300 p-3 rounded-full hover:bg-gray-400 transition-all cursor-pointer'>
                  <img src={close_icon} className='h-3 w-3' alt='close_icon'></img>
                </div>
                <div className='font-bold text-2xl'>Choose folder</div>
                <div className='mt-5 font-medium'>
                  Please select a folder to move your file to:
                </div>
                <div className="box">
                  <ul className="directory-list">
                    {listFolders()}
                  </ul>
                </div>
                <div>
                  Selected Folder: <b>{selectedFolder()}</b>
                </div>
                <div className='mt-5 space-x-3'>
                  <button onClick={() => moveFile()} className='px-5 py-2 bg-green-500 text-white rounded-md hover:bg-green-600 transition-all'>Confirm</button>
                  <button onClick={() => setShow(false)} className='px-5 py-2 bg-gray-500 text-white rounded-md hover:bg-gray-600 transition-all'>Cancel</button>
                </div>
              </div>
            </div>
          }
        </>
      )
    }

    const moveFile = () => {
      var filePath = this.state.selectedFile.key;
      var destination = this.state.selectedFolder;

      filePath = filePath.slice(filePath.indexOf("/")+1)

      var params = {
        userId: this.state.userid,
        originalFile: filePath  
      }
  
      if (destination !== "") {
        destination = destination.slice(destination.indexOf("/")+1);
        params.newFileLocation = destination;
      }
      
      console.log(params)
      console.log(filePath.replace(this.state.selectedFile.name,""))
      // Check if destination is not the same as current path
      if (destination !== filePath.replace(this.state.selectedFile.name,"")) {
        // Move file to destination
        axios.post(apiLink + "File/move", 
          params, authHeader())
        .then((response) => {
          console.log(response);
          console.log("File Moved Successfully");
          this.setState({moveSuccess: true},() => {
              setTimeout(this.closeModal, 1000);
            }
          );
          // Refresh path
          this.setPath(this.state.currentPath)
        })
      } else {
        alert("File is already in that folder.")
      }
      
      setShowMoveFileDialog(false)
    }

    const selectedFolder = () => {
      var selected = this.state.selectedFolder;
      if (selected === "") {
        return "Root Folder"
      }
      selected = selected.slice(selected.indexOf("/")+1, -1)
      return selected;
    }

    const listFolders = () => {
      var folders = [];
      folders.push(<li key="rootFolder"><button onClick={()=>this.selectFolder("")}>Root Folder</button></li>)
      this.state.folderList.map((file) => {
        if (!file.url) {
          var depth = file.key.match(/\//g).length - 1;
          folders.push(<li style={{marginLeft: 20*depth + 'px'}} key={file.key}><button onClick={()=>this.selectFolder(file.key)}>{file.name}</button></li>)
        }return null
      })
      return folders;
    }

    return (
      <div className="main-div-files">
        {this.state.showMoveFileDialog && 
          <MoveFile show={this.state.showMoveFileDialog} setShow={setShowMoveFileDialog}/>
        }
        {this.state.moveSuccess && (
            <MoveSuccess show={this.state.moveSuccess} />
          )}
        <link
          rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"
        />
        <h1 className="text-5xl text-center pt-10 font-bold text-white">
          My Files Page
        </h1>
        <div id="upload-info">
          <p>
          Upload your mandatory training certificates here.
          You can send them to your new workplace, so that they can recognise your prior learning. 
          Select the certificate you want to send, then put in the email address of the workforce or education unit at your new workplace and hit send certificate!
          </p>
        </div>
        
        <div className="input">
          <label className="block uppercase text-left tracking-wide text-white font-bold mb-7 mt-10 ml-20">
            Upload your file (to current folder)
          </label>
          <form className="upload-form" onSubmit={this.handleUpload}>
            <label className="button-label" htmlFor="file">
              <img
                alt="upload"
                src={uploadicon}
                style={{ height: "1.6em", marginRight: "1.0em" }}
              ></img>
              Upload
            </label>
            <input
              className="upload-file"
              type="file"
              name="file"
              id="file"
              onChange={this.onChange}
            />
            <button className="button" type="submit">
              Submit
            </button>
          </form>

          <label className="block uppercase text-left tracking-wide text-white font-bold mb-7 ml-20 ">
            Create a folder to store your file
          </label>
          <div className="createFolder-form">
            <form onSubmit={createFolder}>
              <input
                className="upload-file"
                type="text"
                name="folderName"
                id="folderName"
                placeholder="Folder Name"
                onChange={this.handleChange}
                required
              ></input>
              <button className="button" type="submit">
                <i className="fa fa-plus fa-lg" />
                &nbsp;Create Folder
              </button>
            </form>
          </div>
          {/* Conditional Rendering for warning message */}

          {this.state.chosenFile === undefined && this.state.isSubmit && (
            <p className="warning-text">Please select a file!</p>
          )}

          {this.state.uploadSuccess && (
            <SuccessModal show={this.state.uploadSuccess} />
          )}

          <br />
          {/* Table Data for Files */}
          <h1 className="text-center text-3xl py-3 text-white font-bold mb-1">
            My Files
          </h1>
          <p className="text-white mb-5">A list of all your stored files</p>
          <table id="files">
            <thead>
              <tr>
                <th> </th>
                <th>File Name</th>
                <th>Rename</th>
                <th>Modified</th>
                <th>Select Certificate</th>
                <th>Move</th>
                <th>Delete</th>
              </tr>
            </thead>
            <tbody>
              {/* Back Button which shows when inside a subfolder */}
              {backButton()}
              {/* Display folders and files*/}
              {this.state.files.map((file) =>
                this.isNotInSubfolder(file) ? showFile(file) : null
              )}
            </tbody>
          </table>
          {this.state.files.length === 0 ? (
            <div className="text-center font-bold text-xl">
              No files to display
            </div>
          ) : null}
          <div
            className="sendCert-form"
            id="sendCert"
            onSubmit={this.sendCertificate}
          >
            <form id="forward-form-style">
              <div className="text-center text-2xl py-3 text-white font-bold">
                Forward Certificate to an Email
              </div>
              <div className="selected-cert-div">
                <p className="text-white">Selected certificates: </p>{" "}
                <div className="text-blue-500 inline">
                  {this.state.certificates.length !== 0 ? 
                    (this.state.certificates.map((cert) => <div key={cert.key}>{cert.name}</div>))
                    : "None Selected"}
                </div>
              </div>
              <label className="block uppercase tracking-wide text-white font-bold mb-2">
                Name of recipient
              </label>
              <input
                className="upload-file"
                type="text"
                name="recipientName"
                id="recipientName"
                placeholder="e.g. John"
                onChange={this.handleChange}
                required
              ></input>
              <label className="block uppercase tracking-wide text-white font-bold mb-2">
                Recipient e-mail
              </label>
              <input
                className="upload-file"
                type="email"
                name="emailRecipient"
                id="emailRecipient"
                placeholder="example@gmail.com"
                onChange={this.handleChange}
                required
              ></input>
              <div>
                <label className="block uppercase tracking-wide text-white font-bold mb-2">
                  Personalised message
                </label>
                <textarea
                  name="message"
                  id="message"
                  placeholder="Type your message here"
                  onChange={this.handleChange}
                  required
                ></textarea>
              </div>
              <div className="text-red-500 text-s italic" role="alert">
                {this.state.certMsg}
              </div>
              <button className="button" type="submit">
                Send Certificate
              </button>
            </form>
          </div>
        </div>
      </div>
    );
  }
}

export default Files;
