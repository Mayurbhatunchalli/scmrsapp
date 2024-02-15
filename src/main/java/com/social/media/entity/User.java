package com.social.media.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String profilePicture;
	
	private Set<Integer> followers = new HashSet<>();

	private Set<Integer> followings = new HashSet<>();
	
	@ManyToMany
	@JsonIgnore
	private List<Post> savedPost = new ArrayList<>();
	
	public User() {

	}

	public User(int id, String firstName, String lastName, String email, String password, String profilePicture,
			Set<Integer> followers, Set<Integer> followings, List<Post> savedPost) {
		
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.profilePicture = profilePicture;
		this.followers = followers;
		this.followings = followings;
		this.savedPost = savedPost;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Post> getSavedPost() {
		return savedPost;
	}

	public void setSavedPost(List<Post> savedPost) {
		this.savedPost = savedPost;
	}

	public Set<Integer> getFollowers() {
		return followers;
	}

	public void setFollowers(Set<Integer> followers) {
		this.followers = followers;
	}

	public Set<Integer> getFollowings() {
		return followings;
	}

	public void setFollowings(Set<Integer> followings) {
		this.followings = followings;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + ", followers=" + followers + ", followings=" + followings + ", savedPost="
				+ savedPost + "]";
	}
	
}
