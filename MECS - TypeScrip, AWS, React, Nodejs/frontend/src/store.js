import { applyMiddleware, compose } from "redux";
import { legacy_createStore } from '@reduxjs/toolkit'
import thunk from "redux-thunk";
import rootReducer from "./reducers";

const middleware = [thunk];

const store = legacy_createStore(
  rootReducer,
  compose(applyMiddleware(...middleware))
);

export default store;
