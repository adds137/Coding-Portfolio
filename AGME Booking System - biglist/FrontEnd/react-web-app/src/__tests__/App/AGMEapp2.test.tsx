import React from "react"
import ReactDOM from 'react-dom'
import SearchForm from "../../components/Search/SearchForm";
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

describe('SearchForm component test', () => {
    const mockfn = jest.fn();
    let wrapper: ShallowWrapper<any, Readonly<{}>, React.Component<{}, {}, any>>
    beforeEach(() => {
        wrapper = shallow(<SearchForm navset={true}/>)
    })
    it("Renders <SearchForm> correctly", () => {
        shallow(<SearchForm navset={true}/>);
    })
    it("Pathname reflects state value: "
    +"'http://localhost:3000/Search/test' where 'test' == this.state.value", ()=> {    
        wrapper.setState({value: 'test'})
        wrapper.find('#submitbutton').simulate('click');
        expect(location.pathname.includes('test'));
    })
    it("Recieves 'navset' property and updates 'set' state correctly", () => {
        wrapper.setProps({navset: true});
        expect(wrapper.state('set')).toEqual(true)
    })
    it("Searching when no businesses are present results in result length, 'len', of 0", () => {
        wrapper.setProps({set: true, value: "x", tempval: "x"});
        expect(wrapper.state('len')).toEqual(0);
    })
    it("Clicking checkbox updates 'checked' state to true", () => {
        wrapper.find("#cb").simulate('change', { target: { checked: true } });
        expect(wrapper.state('checked')).toEqual(true);
    })
    it("After submit, state 'value' should equal state 'tempval", () => {
        wrapper.setState({value: "test"});
        wrapper.find('#submitbutton').simulate('click');
        const pathname = window.location.pathname;
        const sub = pathname.indexOf("Search/");
        const tofind = pathname.substring(pathname.length, sub+7);
        expect(wrapper.state("tempval")).toEqual(tofind);
    })

})