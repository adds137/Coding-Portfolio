import React from "react"
import BusinessPage from "../../components/Business/BusinessPage";

const { shallow, mount } = require("enzyme")
const Enzyme = require("enzyme")
const Adapter = require("enzyme-adapter-react-16");

Enzyme.configure({ adapter: new Adapter()}); 

// test if the edit businessPage component render properly
describe('<BusinessPage /> component unit test', () => {
    it('should render correctly', () =>{
        const wrapper = shallow(<BusinessPage />);
        expect(wrapper).toMatchSnapshot();
    })
})

// test if the state save the id correctly
describe('<BusinessPage /> component unit test', () => {
    const mockfn = jest.fn();
    const state = {
        businessId: 1
    };
    it("should have businessid of 1", ()=>{
        const component = shallow(<BusinessPage {... state} />)
        expect(component).toHaveLength(1);
    })
})
