package com.ss.academy.java.dao.comment;

import java.util.List;

import com.ss.academy.java.model.comment.Comment;

public interface CommentDao {
	
	Comment findById(Integer comment_id);
	
	void saveComment(Comment comment);
	
	void deleteCommentById(Integer comment_id);
	
	List<Comment> findAllComments();
}
