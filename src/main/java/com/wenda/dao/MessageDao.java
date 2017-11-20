package com.wenda.dao;

import com.wenda.model.Message;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by wanganyu on 2017/11/10.
 */
@Mapper
public interface MessageDao {
    String TABLE_NAME=" message ";
    String INSERT_FIELDS=" from_id,to_id,content,has_read,conversation_id,created_date";
    String SELECT_FILEDS=" id, "+INSERT_FIELDS;
    @Insert({"insert into", TABLE_NAME ," (",INSERT_FIELDS,") values (#{fromId},#{toId},#{content},#{hasRead},#{conversationId},#{createdDate})"})
    int addMessage(Message message);



    @Select({"select ",SELECT_FILEDS," from ",TABLE_NAME," where conversation_id=#{conversationId}  order by created_date desc limit #{offset},#{limit}"})
   List<Message> getConversationDetail(@Param("conversationId") String conversationId,@Param("offset") int offset,@Param("limit") int limit);


    @Select({"select ",INSERT_FIELDS,",count(id) as id from (select * from ",TABLE_NAME,"" +
            " where from_id=#{userId} or to_id=#{userId} order by created_date desc) tt  group by conversation_id order by created_date desc limit #{offset}, #{limit}"})
    List<Message> getConversationList(@Param("userId") int userId,@Param("offset") int offset,@Param("limit") int limit);

    @Select({"select count(id) from ",TABLE_NAME," where entity_id=#{entityId} and entity_type=#{entityType} "})
    int getCommentCount(@Param("entityId") int entityId,
                         @Param("entityType") int entityType);

    @Update({"update comment set status=#{status} where id=#{id}"})
    int updateStatus(@Param("id") int id, @Param("status") int status);

    @Select({"select count(id) from ",TABLE_NAME," where has_read=0 and to_id=#{userId} and conversation_id=#{conversationId}"})
    int getConversationUnreadCount(@Param("userId") int userId,@Param("conversationId") String conversationId);

}
