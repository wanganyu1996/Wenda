import com.wenda.WendaApplication;
import com.wenda.dao.QuestionDao;
import com.wenda.dao.UserDao;
import com.wenda.model.EntityType;
import com.wenda.model.Question;
import com.wenda.model.User;
import com.wenda.service.FollowService;
import com.wenda.util.JedisAdapter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.swing.text.html.parser.Entity;
import java.util.Date;
import java.util.Random;

/**
 * Created by wanganyu on 2017/11/10.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WendaApplication.class)
@Sql("/init-schema.sql")
public class InitDatabaseTests {

    @Autowired
    UserDao userDao;
    @Autowired
    QuestionDao questionDao;

    @Autowired
    FollowService followService;

    @Autowired
   JedisAdapter jedisAdapter;

    @Test
    public void initDatabase(){
        Random random=new Random();
        jedisAdapter.getJedis().flushDB();
        for(int i=0;i<10;i++){
            User user=new User();
            user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png",random.nextInt(1000)));
            user.setName(String.format("user%d",i+1));
            user.setPassword("");
            user.setSalt("");
            userDao.addUser(user);
            user.setPassword("xx");
            userDao.updatePassword(user);
            for(int j=1;j<i;++j){
                followService.follow(j, EntityType.ENTITY_USER,i);
            }
        Question question =new Question();
        question.setCommentCount(i);
        Date date=new Date();
        date.setTime(date.getTime()+1000*3600*i);
        question.setCreatedDate(date);
        question.setUserId(i+1);
        question.setTitle(String.format("TITLE{%d}",i));
        question.setContent(String.format("Hahahahahahahaha Content %d",i));
        questionDao.addQuestion(question);
        }
        Assert.assertEquals("xx",userDao.selectById(1).getPassword());
       // userDao.deleteById(1);
        //Assert.assertNull(userDao.selectById(1));
       System.out.print(questionDao.selectLatestQuestions(0,0,10));
    }
}
