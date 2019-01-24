package com.teamproject.db;

import com.teamproject.db.core.Database;
import com.teamproject.bean.Participant;
import com.teamproject.bean.Route;
import com.teamproject.bean.User;
import com.teamproject.db.Interface.ParticipantDAOinterface;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class ParticipantDAO extends Database implements ParticipantDAOinterface {

    private static ParticipantDAO participantDAO = null;

    private ParticipantDAO() {
    }

    public static ParticipantDAO getInstance() {
        if (participantDAO == null) {
            participantDAO = new ParticipantDAO();
        }
        return participantDAO;
    }

//    public int createParticipant(int route_id , int user_id) {
//        System.out.println("HEREEEEEEEEEEEEEEEEEEEEEee");
//        Connection conn = createConnection();
//        PreparedStatement prest = null;
//        int rowsInserted = 0;
//        String query = "INSERT INTO `Participants` (`route_id`,`user_id`)"
//                + "VALUES (?,?);";
//        try {
//            prest = conn.prepareStatement(query);
//            prest.setInt(1, route_id);
//            prest.setInt(2, user_id);
//            rowsInserted = prest.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return rowsInserted;
//    }
    
    @Override
    public int createParticipant(Route route, User user) {
        Connection conn = createConnection();
        PreparedStatement prest = null;
        int rowsInserted = 0;
        String query = "INSERT INTO `Participants` (`route_id`,`user_id`)"
                + "VALUES (?,?);";
        System.out.println(query);
        try {
            prest = conn.prepareStatement(query);
            prest.setInt(1, route.getId());
            prest.setInt(2, user.getId());
            rowsInserted = prest.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsInserted;
    }
    

    public int createParticipant(int routeid, int userid) {
        Connection conn = createConnection();
        PreparedStatement prest = null;
        int rowsInserted = 0;
        String query = "INSERT INTO `Participants` (`route_id`,`user_id`)"
                + "VALUES (?,?);";
//        System.out.println(query);
        try {
            prest = conn.prepareStatement(query);
            prest.setInt(1, routeid);
            prest.setInt(2, userid);
            rowsInserted = prest.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsInserted;
    }

    @Override
    public void updateParticipant(int id, int route_id, int user_id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    public int deleteParticipant(int routeid, int userid) {
        Connection conn = createConnection();
        PreparedStatement prest = null;
        int rowsDeleted = 0;
        
         String query = "DELETE FROM `teamproject`.`Participants` " +
                        "WHERE `route_id` = (?) AND `user_id` = (?) ;";
        try {
            prest = conn.prepareStatement(query);
            prest.setInt(1, routeid);
            prest.setInt(2, userid);
            rowsDeleted = prest.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsDeleted;
    }

    @Override
    public void deleteParticipantById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public HashMap<Integer, Participant> selectAllParticipants() {
        String query = "SELECT * FROM `teamproject`.`Participants`;";
        return getParticipantfromQuery(query);
    }

    public HashMap<Integer, Participant> getParticipantfromQuery(String query) {

        Collection<Map<String, Object>> answer = new ArrayList<>();
        answer = getGenericSelect(query);

        HashMap<Integer, Participant> participantFound = new HashMap<>();

        for (Map<String, Object> row : answer) {
            Participant participant = new Participant();
            participant.setId((Integer) row.get("id"));
            participant.setRoute_id((Integer) row.get("route_id"));
            participant.setUser_id((Integer) row.get("user_id"));
            participantFound.put(participant.getId(), participant);
        }
        return participantFound;

    }

    public Participant getParticipantById(int id) {
        Participant participant = new Participant();

        String query = ("SELECT * FROM `teamproject`.`Participants` WHERE `id` = '" + id + "';");
        
        return  participant;
    }

    public HashMap<Integer, Participant> selectParticipantById(int id) {

        String query = "SELECT * FROM `teamproject`.`Participants` WHERE `route_id` = '" + id + "';";

        Collection<Map<String, Object>> answer;
        answer = getGenericSelect(query);

        HashMap<Integer, Participant> participantsFound = new HashMap<>();

        for (Map<String, Object> row : answer) {
            Participant participant = new Participant();
            participant.setId((Integer) row.get("id"));
            participant.setRoute_id((Integer) row.get("route_id"));
            participant.setUser_id((Integer) row.get("user_id"));
            participantsFound.put(participant.getId(), participant);
        }

        return participantsFound;
    }
    
    public boolean checkParticipant(int routeId, int userId){
      
        String query = ("SELECT * FROM `teamproject`.`Participants`"+
                        " WHERE `route_id` = '" + routeId + "' AND `user_id` = '" + userId + "';");
     
        return  !getGenericSelect(query).isEmpty();
  
    }

}
