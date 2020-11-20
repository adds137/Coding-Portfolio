import React from "react"
import SearchPage from "../../components/Search/SearchPage";
import { configure, shallow, render, ShallowWrapper, mount } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
import NavSearch from "../../components/HomePage/NavSearch";
import SearchForm from "../../components/Search/SearchForm";

configure({ adapter: new Adapter()}); 

describe('<SearchPage> component test', () => {
    let wrapper: ShallowWrapper<any, Readonly<{}>, React.Component<{}, {}, any>>
    beforeEach(() => {
        wrapper = shallow(<SearchPage match={{params: {searchid: "test"}}}/>);
    })
    it("Renders <SearchPage> correctly", () => {
        shallow(<SearchPage match={{params: {searchid: "test"}}}/>);
    })
    it("Recieves properties correctly", () => {
        wrapper = shallow(<SearchPage searchterm={"test"} match={{params: {searchid: "test"}}}/>);
        expect(wrapper.state('searchprop')).toEqual("test");
    })
    it("Renders <SearchForm> component in <SearchPage> correctly", () => {
        expect(wrapper.find(SearchForm)).toHaveLength(1);
    })
    it("Renders title of <SearchPage>", () => {
        expect(wrapper.find('#searchtitle')).toHaveLength(1);
    })
})

describe('<NavSearch> component test', () => {
    let wrapper: ShallowWrapper<any, Readonly<{}>, React.Component<{}, {}, any>>
    beforeEach(() => {
        wrapper = shallow(<NavSearch/>);
    })
    it("Renders <NavSearch> correctly", () => {
        shallow(<NavSearch/>);
    })
    it("Updates Link path based on 'search' state", () => {
        wrapper.setState({search: "test"});
        const link = wrapper.find('#navsearchlink');
        const path = link.prop('to');
        expect(path).toMatchObject( {"pathname": "/Search/test", "state": {"searchterm": "test"}} );
    })
    it("Clicking search button resets 'search' state value", () => {
        wrapper.setState({search: "ToRemove"});
        const button = wrapper.find('#searchspan');
        button.simulate('click');
        expect(wrapper.state('search')).toEqual('');
    })
    it("Reloads on submit if pathname includes '/Search/'", () => {
        window.history.replaceState({}, 'searchtest', '/Search/test');
        Object.defineProperty(window, 'location', {
            writable: true,
            value: { assign: jest.fn() }
          })
          
          Object.defineProperty(window.location, 'reload', {
            writable: true,
            value: { assign: jest.fn() }
          })
        window.location.pathname = "/Search/test"
        window.location.reload = jest.fn();
        wrapper.find("#searchspan").simulate('click');
        expect(window.location.reload).toHaveBeenCalled();
    }) 

})