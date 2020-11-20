import React from "react"
import { configure, shallow, render, ShallowWrapper, mount } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
import HomePageContent from "../../components/HomePage/HomePageContent";
import HomePageHeader from "../../components/HomePage/HomePageHeader";

configure({ adapter: new Adapter()});

describe("<HomePageContent> component test", () => {
    let wrapper: ShallowWrapper<any, Readonly<{}>, React.Component<{}, {}, any>>
    beforeEach(() => {
        wrapper = shallow(<HomePageContent/>)
    })
    it("Shallow renders without errors", () => {
        shallow(<HomePageContent/>)
    })
    it("Contains a <HomePageHeader> component", () => {
        expect(wrapper.find(HomePageHeader)).toHaveLength(1);
    })
    it("Renders title", () => {
        expect(wrapper.find('#herobanner2small')).toHaveLength(1);
    })
    it("Renders ' No Businesses Found ' if none are found", () => {
        wrapper.setState({businesses: [], loading: false});
        //expect(wrapper.state('businesses.length')).toEqual(undefined);
        expect(wrapper.find("#none").text()).toEqual(" No Businesses Found ");
    })
    it("Renders ' Loading Businesses . . . ' if API is inaccessible", () => {
        wrapper.setState({businesses: [{"id":1,"name":"testbusiness","blurb":"testbusinessblurb","description":"testdescription","address":"testaddress","phoneNumber":"testphonenumber"}]
            , loading: true});
        expect(wrapper.find("#loading").text()).toEqual(" Loading Businesses . . . ");
    })

})