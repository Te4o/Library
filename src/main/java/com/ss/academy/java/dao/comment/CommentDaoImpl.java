package com.ss.academy.java.dao.comment;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.ss.academy.java.dao.AbstractDao;
import com.ss.academy.java.model.comment.Comment;

@Repository("commentDao")
public class CommentDaoImpl extends AbstractDao<Integer, Comment> implements CommentDao {

	public Comment findById(Integer comment_id){
	Comment comment = getByKey(comment_id);
	
	return comment;
	}
	
	public void saveComment(Comment comment){
		save(comment);
	}
	
	public void deleteCommentById(Integer comment_id){
		Query query = getSession().createSQLQuery("delete from comments where comment_id = :comment_id");
		query.setInteger("comment_id", comment_id);
		query.executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	public List<Comment> findAllComments() {
		Criteria criteria = createEntityCriteria();
		List<Comment> comments = (List<Comment>) criteria.list();
		
		return comments;
	}
}
