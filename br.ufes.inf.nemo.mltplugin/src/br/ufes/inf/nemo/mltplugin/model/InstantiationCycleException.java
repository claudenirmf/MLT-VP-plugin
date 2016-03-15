package br.ufes.inf.nemo.mltplugin.model;

public class InstantiationCycleException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ClassWrapper element;
	
	public InstantiationCycleException(ClassWrapper element) {
		this.element = element;
	}

	public String getName() {
		return element.getName();
	}

	public String getId() {
		return element.getId();
	}

}
