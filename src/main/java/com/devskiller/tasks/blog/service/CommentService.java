package com.devskiller.tasks.blog.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.devskiller.tasks.blog.model.Comment;
import com.devskiller.tasks.blog.model.Post;
import com.devskiller.tasks.blog.repository.PostCommentRepository;
import com.devskiller.tasks.blog.repository.PostRepository;
import org.springframework.stereotype.Service;

import com.devskiller.tasks.blog.model.dto.CommentDto;
import com.devskiller.tasks.blog.model.dto.NewCommentDto;

@Service
public class CommentService {

   private final PostRepository postRepository;
   private final PostCommentRepository postCommentRepository;
   public CommentService(PostRepository postRepository,PostCommentRepository postComRep)
   {
	   this.postRepository=postRepository;
	   this.postCommentRepository=postComRep;
   }
	/**
	 * Returns a list of all comments for a blog post with passed id.
	 *
	 * @param postId id of the post
	 * @return list of comments sorted by creation date descending - most recent first
	 */

	public List<CommentDto> getCommentsForPost(Long postId) {

    return postCommentRepository.findById(postId).map(mp->new CommentDto(mp.getId(),mp.getContent(),mp.getAuthor(),mp.getCreationDate())).stream().sorted(Collections.reverseOrder()).collect(Collectors.toList());

		 //.map(mp->new CommentDto(mp.getId(),mp.,mp.getAuthor(),mp.getCreationDate())).collect(Collectors.toList());
	}

	/**
	 * Creates a new comment
	 *
	 * @param postId id of the post
	 * @param newCommentDto data of new comment
	 * @return id of the created comment
	 *
	 * @throws IllegalArgumentException if postId is null or there is no blog post for passed postId
	 */
	public Long addComment(Long postId, NewCommentDto newCommentDto) {
       Post post=postRepository.findById(postId).get();
		Comment comment=new Comment();
		comment.setPost(post);
		comment.setAuthor(newCommentDto.author());
		comment.setContent(newCommentDto.content());
  	return postCommentRepository.save(comment).getId();
	}
}
