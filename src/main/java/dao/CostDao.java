package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.cost;
import util.DBUtil;

public class CostDao implements Serializable {
	public List<cost> findAll(){
		Connection conn = null;
		try {
			conn = DBUtil.getConnecion();
			String sql = 
					"select * From cost"
					+ " order by cost_id";
			Statement smt = 
					conn.createStatement();
			ResultSet rs = smt.executeQuery(sql);
			List<cost> list = new ArrayList<cost>();
			while(rs.next()) {
				cost c = new cost();
				c.setCostID(rs.getInt("cost_id"));
				c.setName(rs.getString("name"));
				c.setBaseDuration(rs.getInt("base_duration"));
				c.setBaseCost(rs.getDouble("base_cost"));
				c.setUnitCost(rs.getDouble("unit_cost"));
				c.setStatus(rs.getString("status"));
				c.setDescr(rs.getString("descr"));
				c.setCreatime(rs.getTimestamp("creatime"));
				c.setStartime(rs.getTimestamp("startime"));
				c.setCostType(rs.getString("cost_type"));
				list.add(c);
			}
			return list;
		} catch (SQLException e) {	
			e.printStackTrace();
			throw new RuntimeException(
					"查询资费失败",e);
		}finally {
			DBUtil.close(conn);
		}
	}
	public static void main(String[] args) {
		CostDao costDao = new CostDao();
		List<cost> list = costDao.findAll();
		for(cost cost : list) {
			System.out.println(
					cost.getCostID()+"名字"+
					cost.getName()+cost.getBaseDuration()+cost.getBaseCost()+
					cost.getUnitCost()+cost.getStatus()+cost.getDescr()
					+cost.getCreatime()+cost.getStartime()+cost.getCostType()
					);
			
		}
		System.out.println();
	}
}
