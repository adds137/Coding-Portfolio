import React from "react"
import ReactDOM from 'react-dom'
import { configure, shallow, render, ShallowWrapper, mount } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
import App from "../../App";

configure({ adapter: new Adapter()}); 

describe("AGME Application Test", () => {
    it("<App> renders without crashing", () => {
        const div = document.createElement('div');
        ReactDOM.render(<App/>, div);
    })
    it("<App> shallow renders without errors", () => {
        shallow(<App/>)
    })
})