package org.ksun.pets.udemy.course.instademo.service;

import org.ksun.pets.udemy.course.instademo.dto.PostDTO;
import org.ksun.pets.udemy.course.instademo.entity.ImageModel;
import org.ksun.pets.udemy.course.instademo.entity.Post;
import org.ksun.pets.udemy.course.instademo.entity.User;
import org.ksun.pets.udemy.course.instademo.exceptions.PostNotFoundException;
import org.ksun.pets.udemy.course.instademo.repository.ImageRepository;
import org.ksun.pets.udemy.course.instademo.repository.PostRepository;
import org.ksun.pets.udemy.course.instademo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    public static final Logger LOGGER = LoggerFactory.getLogger(PostService.class);

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository, ImageRepository imageRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
    }

    public Post createPost(PostDTO postDTO, Principal principal) {
        User user = gerUserByPrincipal(principal);
        Post post = new Post();
        post.setUser(user);
        post.setCaption(postDTO.getCaption());
        post.setLoation(postDTO.getLocation());
        post.setTitle(postDTO.getTitle());
        post.setLikes(0);
        LOGGER.info("Saving Post");
        return postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAllByOrderByCreatedDateDesc();
    }

    public Post getPostById(Long postId, Principal principal) {
        User user = gerUserByPrincipal(principal);
        return postRepository.findPostByIdAndUser(postId, user)
                .orElseThrow(() -> new PostNotFoundException("PostNotFoundException id = " + postId + "user" + user.getUsername()));
    }


    public List<Post> getAllPostsForUser(Principal principal) {
        User user = gerUserByPrincipal(principal);
        return postRepository.findAllByUserOrderByCreatedDateDesc(user);
    }

    public Post likePost(Long id, String username) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("PostNotFoundException id = " + id));

        Optional<String> userLiked = post.getLikedUsers()
                .stream()
                .filter(u -> u.equals(username))
                .findAny();

        if (userLiked.isPresent()) {
            post.setLikes(post.getLikes() - 1);
            post.getLikedUsers().remove(username);
        } else {
            post.setLikes(post.getLikes() + 1);
            post.getLikedUsers().add(username);
        }
        return postRepository.save(post);
    }

    public void deletePost(Long postId, Principal principal){
        Post post = getPostById(postId, principal);
        Optional<ImageModel> imageModel = imageRepository.findByPostId(post.getId());
        postRepository.delete(post);
        imageModel.ifPresent(imageRepository::delete);
        
    }


    private User gerUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUserByUsername(username)
                .orElseThrow(()
                        -> new UsernameNotFoundException("Username = " + username + " Not Found"));
    }

}
