package com.standard.workshopmongo.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.standard.workshopmongo.domain.Post;
import com.standard.workshopmongo.domain.User;
import com.standard.workshopmongo.dto.AuthorDTO;
import com.standard.workshopmongo.dto.CommentDTO;
import com.standard.workshopmongo.repository.PostRepository;
import com.standard.workshopmongo.repository.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;
		
	@Override
	public void run(String... args) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		userRepository.deleteAll();
		postRepository.deleteAll();
		
		User maria = new User(null, "Maria Marrom", "ma@ria.com");
		User alex = new User(null, "Alex Verde", "a@lex.com");
		User bob = new User(null, "Beto Cinza", "qual@quercoisa.com");
		
		userRepository.saveAll(Arrays.asList(maria, alex, bob));
		
		Post post1 = new Post(null, sdf.parse("29/07/2019"), "Viagem", "Vou viajar! #peideiesaí", new AuthorDTO(maria));
		Post post2 = new Post(null, sdf.parse("31/07/2019"), "Bom dia", "Acordei feliz!!!!!", new AuthorDTO(maria));
		
		CommentDTO c1 = new CommentDTO("Divirta-se! ", sdf.parse("01/08/2019"), new AuthorDTO(alex));
		CommentDTO c2 = new CommentDTO("Nem chama??? ", sdf.parse("31/07/2019"), new AuthorDTO(bob));
		CommentDTO c3 = new CommentDTO("quem te perguntou? ", sdf.parse("30/07/2019"), new AuthorDTO(alex));
		
		post1.getComments().addAll(Arrays.asList(c1,c2));
		post2.getComments().addAll(Arrays.asList(c3));
		
		postRepository.saveAll(Arrays.asList(post1, post2));
		
		maria.getPosts().addAll(Arrays.asList(post1, post2));
		userRepository.save(maria);
		
	}

}
