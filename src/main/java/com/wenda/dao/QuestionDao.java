package com.wenda.dao;

import com.wenda.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by wanganyu on 2017/11/10.
 */
@Mapper
public interface QuestionDao {
    String TABLE_NAME=" question ";
    String INSERT_FIELDS="title,content,created_date,user_id,comment_count";
    String SELECT_FILEDS=" id, "+INSERT_FIELDS;
    @Insert({"insert into", TABLE_NAME ," (",INSERT_FIELDS,") values (#{title},#{content},#{createdDate},#{userId},#{commentCount})"})
    int addQuestion(Question question);

   List<Question> selectLatestQuestions(@Param("userId") int userId,
                                        @Param("offset")int offset,
                                        @Param("limit") int limit);



}
