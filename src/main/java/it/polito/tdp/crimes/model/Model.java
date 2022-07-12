package it.polito.tdp.crimes.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	private EventsDao dao;
	private Graph<ReatoQuartieri,DefaultWeightedEdge> grafo;
	private List<Arco> archi;
	List<ReatoQuartieri> best;
	
	public Model() {
		this.dao= new EventsDao();
		
	}
	
	public List<String> popolaCat(){
		return this.dao.popolaOffenceCategory();
	}
	
	public List<Integer> popolaMesi(){
		return this.dao.getData();
	}
	
	public void creaGrafo(String categoria, Integer mese) {
		
		this.grafo= new SimpleWeightedGraph<ReatoQuartieri,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		this.archi=new ArrayList<Arco>();
		Graphs.addAllVertices(this.grafo, this.dao.getVertici(categoria, mese));
		
		for(ReatoQuartieri r1: this.grafo.vertexSet()) {
			for(ReatoQuartieri r2: this.grafo.vertexSet()) {
				if(!r1.equals(r2)) {
					Arco a= this.dao.getArco(r1, r2);
					if( a!= null && a.getPeso()!=0) {
					Graphs.addEdgeWithVertices(this.grafo, r1, r2, a.getPeso());
					this.archi.add(a);
					}
				}
			}
			
		}
		
	}
	
	public List<Arco> getArchi() {
		return archi;
	}

	public void setArchi(List<Arco> archi) {
		this.archi = archi;
	}

	public String nVertici() {
		return "Grafo creato!"+"\n"+"#verici: "+ this.grafo.vertexSet().size()+"\n";
	}
	
	public String nArchi() {
		return "#archi: "+ this.grafo.edgeSet().size()+"\n";
	}
	
	public List<Arco> maggiorePesoMedio(){
		double pesoMed=0;
		int tot=0;
		List<Arco> list= new ArrayList<>();
		for(DefaultWeightedEdge e: this.grafo.edgeSet()) {
			tot+=this.grafo.getEdgeWeight(e);
		}
		pesoMed= tot/(this.grafo.edgeSet().size());
		for(DefaultWeightedEdge e: this.grafo.edgeSet()) {
			if(this.grafo.getEdgeWeight(e)>pesoMed) {
				ReatoQuartieri r1= this.grafo.getEdgeSource(e);
				ReatoQuartieri r2= this.grafo.getEdgeTarget(e);
				Arco a= new Arco(r1,r2 ,(int) this.grafo.getEdgeWeight(e));
				list.add(a);
			}
		}
		return list;
		
	}
	
	public List<ReatoQuartieri> calcolaPercorso(Arco a){
		this.best= new ArrayList<ReatoQuartieri>();
		this.best.add(a.getQ1());
		List<ReatoQuartieri> parziale=new ArrayList<ReatoQuartieri>();
		parziale.add(a.getQ1());
		cerca(parziale,a.getQ2());
		
		return best;
		
	}

	private void cerca(List<ReatoQuartieri> parziale, ReatoQuartieri q2) {
		if(parziale.get(parziale.size()-1).equals(q2)) {
			if(parziale.size()>best.size() ) {
				this.best=new ArrayList<ReatoQuartieri>(parziale);
				return;
			}
		}
		
		for(ReatoQuartieri qi: Graphs.neighborListOf(this.grafo, parziale.get(parziale.size()-1))) {
			
			if(!parziale.contains(qi)) {
				parziale.add(qi);
				cerca(parziale, q2);
				parziale.remove(parziale.size()-1);
			}
		}
	}
	
}
