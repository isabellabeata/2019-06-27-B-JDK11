package it.polito.tdp.crimes.model;

public class ReatoQuartieri {
	
	private String offense_type_id;

	
	public ReatoQuartieri(String offense_type_id) {
		super();
		this.offense_type_id = offense_type_id;
	}

	public String getOffense_type_id() {
		return offense_type_id;
	}

	public void setOffense_type_id(String offense_type_id) {
		this.offense_type_id = offense_type_id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((offense_type_id == null) ? 0 : offense_type_id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReatoQuartieri other = (ReatoQuartieri) obj;
		if (offense_type_id == null) {
			if (other.offense_type_id != null)
				return false;
		} else if (!offense_type_id.equals(other.offense_type_id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return offense_type_id;
	}
	
	

}
