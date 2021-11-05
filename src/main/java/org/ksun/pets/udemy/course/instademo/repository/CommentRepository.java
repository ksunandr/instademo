package org.ksun.pets.udemy.course.instademo.repository;

import org.ksun.pets.udemy.course.instademo.entity.Comment;
import org.ksun.pets.udemy.course.instademo.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long>
{
    List<Comment> findAllByPost(Post post);

    Comment findByIdAndUserId(Long commentId, Long userId);
}
