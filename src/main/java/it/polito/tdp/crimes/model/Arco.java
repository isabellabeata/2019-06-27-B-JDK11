package it.polito.tdp.crimes.model;

public class Arco {
	
	private ReatoQuartieri q1;
	private ReatoQuartieri q2;
	private int peso;
	public Arco(ReatoQuartieri q1, ReatoQuartieri q2, int peso) {
		super();
		this.q1 = q1;
		this.q2 = q2;
		this.peso = peso;
	}
	public ReatoQuartieri getQ1() {
		return q1;
	}
	public void setQ1(ReatoQuartieri q1) {
		this.q1 = q1;
	}
	public ReatoQuartieri getQ2() {
		return q2;
	}
	public void setQ2(ReatoQuartieri q2) {
		this.q2 = q2;
	}
	public int getPeso() {
		return peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	@Override
	public String toString() {
		return q1.toString()+"--"+q2.toString();
	}
	
	

}
