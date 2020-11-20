import React from "react"
import EditUser from "../../components/User/EditUser";
import { configure, shallow, render, ShallowWrapper, mount } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';

configure({ adapter: new Adapter()}); 

describe('<EditUser> component test', () => {
    let wrapper: ShallowWrapper<any, Readonly<{}>, React.Component<{}, {}, any>>
    beforeEach(() => {
        wrapper = shallow(<EditUser/>);
    })
    it("Shallow renders without errors", () => {
        wrapper;
    })
    it("Renders all inputs", () => {
        expect(wrapper.find('.input')).toHaveLength(3);
    })
})