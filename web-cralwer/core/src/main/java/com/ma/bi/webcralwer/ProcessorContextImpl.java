/**
 * 
 */
package com.ma.bi.webcralwer;

/**
 * @author Vicente Yuen
 *
 */
class ProcessorContextImpl implements ProcessorContext {

	/* (non-Javadoc)
	 * @see com.ma.bi.webcralwer.ProcessorContext#getState()
	 */
	@Override
	public State getState() {
		// TODO Auto-generated method stub
		return state;
	}
	
	private State state;
	
	void setState(State state) {
		this.state = state;
	}
	
	

}
