import React from 'react'
import success_icon from '../../images/success-icon.png';


function SuccessModal({ show }) {
	
	return (
		<>
			{show && 
				<div className="fixed top-0 left-0 right-0 bottom-0 flex items-center justify-center p-10 bg-black bg-opacity-25 z-40">
					<div className='bg-white p-10 rounded-lg max-w-2xl z-50 relative'>
						<div className='font-bold text-2xl text-center'>Upload Success</div>
						<div className='mt-5 font-medium'>
							<img src={success_icon} className=' w-48 object-center' alt='success-icon'></img>
						</div>
					</div>
				</div>
			}
		</>
	)
}

export default SuccessModal