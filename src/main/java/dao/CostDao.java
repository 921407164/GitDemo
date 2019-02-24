package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
	//增加资费
	public void save(cost cost) {
		Connection n = null;
		try {
			n = DBUtil.getConnecion();
			String sql = 
					"insert into cost values("
					+"cost_seq.nextval,"
					+ "?,?,?,?,'1',?,sysdate,null,?)" ;
			PreparedStatement ps = n.prepareStatement(sql);
			ps.setString(1, cost.getName());
			ps.setObject(2, cost.getBaseDuration());
			ps.setObject(3, cost.getBaseCost());
			ps.setObject(4, cost.getUnitCost());
			ps.setString(5, cost.getDescr());
			ps.setString(6,cost.getCostType());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("请求数据库失败");
		} finally {
			DBUtil.close(n);
		}
		
	}
	//根据id查询cost
	public cost findcost(int costId){
		Connection conn = null;
		List<cost> list = new ArrayList<cost>();
		cost c = null;
		try {
			conn = DBUtil.getConnecion();
			String sql = 
					  "select * from cost "
					+ "where cost_id="
					+ costId;
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				c = new cost();
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
				
			}
		} catch (SQLException e) {
		
			e.printStackTrace();
		} finally {
			DBUtil.close(conn);
		}
		return c;
	}
	//修改资费
	public void update(cost cost) {
		if(cost == null)
			return;
		Connection con = null;
		try {
			con = DBUtil.getConnecion();
			String sql = 
				"update cost set " +
				"	name=?," +
				"	base_duration=?," +
				"	base_cost=?," +
				"	unit_cost=?," +
				"	descr=?," +
				"	cost_type=? " +
				"where cost_id=?";
			PreparedStatement ps = 
				con.prepareStatement(sql);
			ps.setString(1, cost.getName());
			ps.setObject(2, cost.getBaseDuration());
			ps.setObject(3, cost.getBaseCost());
			ps.setObject(4, cost.getUnitCost());
			ps.setString(5, cost.getDescr());
			ps.setString(6, cost.getCostType());
			ps.setInt(7, cost.getCostID());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(
				"修改资费失败");
		} finally {
			DBUtil.close(con);
		}
	}
	//删除资费
	public void delete(int costId) {
		Connection conn = null;
		try {
			conn = DBUtil.getConnecion();
			String sql = 
					  "delete from cost"
					+ " where cost_id=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, costId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(
					"删除失败");
		} finally {
			DBUtil.close(conn);
		}
	}
	
	
	//用来测试Dao的
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
		cost cost = new cost();
		cost.setName("66元套餐");
		cost.setBaseDuration(660);
		cost.setBaseCost(66.0);
		cost.setUnitCost(0.6);
		cost.setDescr("66元套餐实惠");
		cost.setCostType("2");
		costDao.save(cost);
		
		System.out.println("根据id查询");
		int id = 1;
		cost c = costDao.findcost(id);
		System.out.println(c.getName());
		
	}
	
	
}
