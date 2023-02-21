import React, { useCallback } from 'react'
import close_icon from '../../images/close_icon.png';
import { useDispatch } from "react-redux";
import { logout } from "../../actions/auth";
import { useNavigate } from 'react-router-dom';


function Logout({ show, setShow }) {
	const dispatch = useDispatch();
	const navigate = useNavigate();

	
	const logOut = useCallback(() => {
		dispatch(logout());
	}, [dispatch]);
	
	const handleLogout = () => {
		logOut();
		setShow(false);
		navigate("/login");
	}
	
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
						<div className='font-bold text-2xl'>Are you sure?</div>
						<div className='mt-5 font-medium'>
						Session will be ended after logging out and you will need to login again
						</div>

						<div className='mt-5 space-x-3'>
							<button onClick={handleLogout} className='px-5 py-2 bg-red-500 text-white rounded-md hover:bg-red-600 transition-all'>Logout</button>
							<button onClick={() => setShow(false)} className='px-5 py-2 bg-gray-500 text-white rounded-md hover:bg-gray-600 transition-all'>Cancel</button>
						</div>
					</div>
				</div>
			}
		</>
	)
}

export default Logout