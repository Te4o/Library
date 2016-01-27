package com.ss.academy.java.service.comment;

import java.util.List;

import com.ss.academy.java.model.comment.Comment;

public interface CommentService {

	Comment findById(Integer comment_id);

	void saveComment(Comment comment);

	void deleteCommentById(Integer comment_id);

	List<Comment> findAllComments();
}
