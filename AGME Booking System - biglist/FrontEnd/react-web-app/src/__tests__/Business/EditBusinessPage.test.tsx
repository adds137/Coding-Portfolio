import React from "react"
import EditBusinessPage from "../../components/Business/EditBusinessPage"

const { shallow, mount } = require("enzyme")
const Enzyme = require("enzyme")
const Adapter = require("enzyme-adapter-react-16")

Enzyme.configure({ adapter: new Adapter() })

describe('<EditBusinessPage /> component unit test', () => {
    it('should render correctly', () =>{
        const wrapper = shallow(<EditBusinessPage />);
        expect(wrapper).toMatchSnapshot();
    })
})

describe('<EditBusinessPage /> component unit test', () => {
    const mockfn = jest.fn();
    const state = {
        businessId: 1
    };
    it("should have a businessid of 1", () =>{
        const component = shallow(<EditBusinessPage {... state} />)
        expect(component).toHaveLength(1);
    })
})