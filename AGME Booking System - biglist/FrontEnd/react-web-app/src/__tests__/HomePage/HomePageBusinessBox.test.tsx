import React from "react"
import HomePageBusinessBox from "../../components/HomePage/HomePageBusinessBox";
import { configure, shallow, render, ShallowWrapper, mount } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';

configure({ adapter: new Adapter()}); 

describe('<HomePageBusinessBox> component test', () => {
    let wrapper: ShallowWrapper<any, Readonly<{}>, React.Component<{}, {}, any>>
    beforeEach(() => {
        wrapper = shallow(<HomePageBusinessBox name={"."} id={0} desc={"."} phoneNumber={"."}/>);
    })
    it("Shallow renders without errors", () => {
        shallow(<HomePageBusinessBox name={"."} id={0} desc={"."} phoneNumber={"."}/>);
    })
    it("Receives name property correctly", () => {
        const box = shallow(<HomePageBusinessBox name={"NAME"} id={0} desc={"."} phoneNumber={"."}/>);
        expect(box.state('name')).toEqual("NAME");
    })
    it("Receives id property correctly", () => {
        const box = shallow(<HomePageBusinessBox name={"."} id={5} desc={"."} phoneNumber={"."}/>);
        expect(box.state('id')).toEqual(5);
    })
    it("Receives desc property correctly", () => {
        const box = shallow(<HomePageBusinessBox name={"."} id={0} desc={"DESC"} phoneNumber={"."}/>);
        expect(box.state('desc')).toEqual("DESC");
    })
    it("Recieves name property correctly", () => {
        const box = shallow(<HomePageBusinessBox name={"."} id={0} desc={"."} phoneNumber={"PNUM"}/>);
        expect(box.state('phoneNumber')).toEqual("PNUM");
    })
    it("All headings displayed", () => {
        expect(wrapper.find(".heading")).toHaveLength(4);
    })
})