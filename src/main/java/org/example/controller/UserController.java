package org.example.controller;

import jakarta.servlet.http.HttpSession;
import org.aspectj.weaver.ast.Not;
import org.example.entity.Notes;
import org.example.entity.UserDtls;
import org.example.repository.NotesRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NotesRepository notesRepository; //to save notes into database
    @ModelAttribute    //so that we can use it in every page
    public void addCommnData(Principal p, Model m){   //Principle is class in java security to get which user is logged in
        String email = p.getName();
        UserDtls user = userRepository.findByEmail(email);
        m.addAttribute("user", user);   //to show user name after login
        //to get the data of user who is logged in
    }

    @GetMapping("/addNotes")
    public String home(){

        return "user/add_notes";
    }
    @GetMapping("/viewNotes/{page}") // particular page
    public String viewNotes(@PathVariable int page, Model m, Principal p){    //to view the notes of the user who is logged in, int page to see which page data it is
        String email = p.getName();
        UserDtls user = userRepository.findByEmail(email); //we get object of user who is logged in

        Pageable pageable = PageRequest.of(page, 5, Sort.by("id").descending()); //pagination
        Page<Notes> notes =  notesRepository.findByNotesByUser(user.getId(), pageable); //in notes repository

        m.addAttribute("pageNo", page);
        m.addAttribute("totalPage", notes.getTotalPages());
        m.addAttribute("Notes", notes);
        m.addAttribute("totalElement", notes.getTotalElements());
        return "user/view_notes";
    }

    @GetMapping("/editNotes/{id}")
    public String editNotes(@PathVariable int id, Model m){
        Optional<Notes> n =notesRepository.findById(id);
        if (n!=null){
            Notes notes = n.get();
            m.addAttribute("notes", notes);
        }

        return "user/edit_notes";
    }
    @PostMapping("/updateNotes")
    public String updateNotes(@ModelAttribute Notes notes, HttpSession session ,Principal p){
        String email = p.getName();
        UserDtls user = userRepository.findByEmail(email);

        notes.setUserDtls(user);
        Notes updateNotes = notesRepository.save(notes);

        if(updateNotes !=null){
            session.setAttribute("msg","Note Updated Successfully");
        }
        else {
            session.setAttribute("msg","Something went wrong OOPS!!");
        }
        return "redirect:/user/viewNotes/0";
    }
    @GetMapping("/deleteNotes/{id}")
    public String deleteNotes(@PathVariable int id, HttpSession session){

        Optional<Notes> notes=notesRepository.findById(id);
        if (notes!=null){
            notesRepository.delete(notes.get());
            session.setAttribute("msg", "Notes Delete Successfully");
        }
        return "redirect:/user/viewNotes/0";
    }
    @GetMapping("/viewProfile")
    public String viewProfile(){

        return "user/view_profile";
    }

    @PostMapping("/saveNotes")
    public String saveNotes(@ModelAttribute Notes notes, HttpSession session , Principal p ){ //principle to get  data of particular user logged in (name)
        String email = p.getName();
        UserDtls u =  userRepository.findByEmail(email); //finding user details
        notes.setUserDtls(u);

        Notes n =  notesRepository.save(notes);
        System.out.println(notes);

        if (n!= null){
            session.setAttribute("msg","Notes Added SUccessfully!! Yahoo");
        }
        else {
            session.setAttribute("msg","OOps something went wromg!");
        }
        return "redirect:/user/addNotes";
    }
    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute UserDtls user, HttpSession session, Model m){

        Optional<UserDtls> Olduser = userRepository.findById(user.getId());
        if(Olduser!=null){
            // the following values will not change
            user.setPassword(Olduser.get().getPassword());
            user.setRole(Olduser.get().getRole());
            user.setEmail(Olduser.get().getEmail());

            UserDtls updateUser = userRepository.save(user);
            if(updateUser != null){
                m.addAttribute("user", updateUser);
                session.setAttribute("msq", "Profile Update Successfully");
            }
        }
        return "redirect:/user/viewProfile";
    }
}

