package it.polito.tdp.crimes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.crimes.model.Arco;
import it.polito.tdp.crimes.model.Event;
import it.polito.tdp.crimes.model.ReatoQuartieri;


public class EventsDao {

	public List<Event> listAllEvents(){
		String sql = "SELECT * FROM events" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;

			List<Event> list = new ArrayList<>() ;

			ResultSet res = st.executeQuery() ;

			while(res.next()) {
				try {
					list.add(new Event(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}

			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}

	public List<String> popolaOffenceCategory(){
		String sql="SELECT DISTINCT e.offense_category_id AS cat "
				+ "FROM events e "
				+ "ORDER BY cat";

		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;

			List<String> list = new ArrayList<>() ;

			ResultSet res = st.executeQuery() ;

			while(res.next()) {
				try {
					list.add(res.getString("cat"));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}

			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}

	public List<Integer> getData(){
		String sql="SELECT distinct Month(e.reported_date) AS dat "
				+ "FROM `events` e "
				+ "ORDER BY dat";

		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;

			List<Integer> list = new ArrayList<>() ;

			ResultSet res = st.executeQuery() ;

			while(res.next()) {
				try {
					list.add(res.getInt("dat"));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}

			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}

	public List<ReatoQuartieri> getVertici(String offense_category, Integer mese){
		String sql="SELECT DISTINCT (e1.offense_type_id) AS tip "
				+ "FROM `events` e1 "
				+ "WHERE e1.offense_category_id=? AND MONTH(e1.reported_date)=? "
				+ "GROUP BY tip "
				+ "ORDER BY tip";
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setString(1, offense_category);
			st.setInt(2, mese);

			List<ReatoQuartieri> list = new ArrayList<>() ;

			ResultSet res = st.executeQuery() ;

			while(res.next()) {
				try {
					list.add(new ReatoQuartieri(res.getString("tip")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}

			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}

	public Arco getArco(ReatoQuartieri q1, ReatoQuartieri q2) {
		String sql="SELECT e2.neighborhood_id "
				+ "FROM events e1,events e2 "
				+ "WHERE e1.offense_type_id=? AND e2.offense_type_id=? "
				+ "AND e1.neighborhood_id=e2.neighborhood_id "
				+ "GROUP BY e2.neighborhood_id";

		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setString(1, q1.toString());
			st.setString(2, q2.toString());
			List<Arco> list= new ArrayList<>();
			Arco a= null;

			ResultSet res = st.executeQuery() ;

			while(res.next()) {

				a= new Arco(q1, q2,0);
				list.add(a);
			}
			if(a!=null) {
				a.setPeso(list.size());
			}
			conn.close();
			return a ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}

	}

}
