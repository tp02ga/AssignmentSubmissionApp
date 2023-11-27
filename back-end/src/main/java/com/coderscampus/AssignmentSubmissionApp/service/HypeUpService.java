package com.coderscampus.AssignmentSubmissionApp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class HypeUpService {

    private static List<String> messages = new ArrayList<>();
    
    public HypeUpService() {
        messages.add("Fantastic effort on getting that done!");
        messages.add("Consistency is key, and you're nailing it!");
        messages.add("Another milestone achieved. Keep up the momentum!");
        messages.add("Every submission takes you one step closer to mastery. Well done!");
        messages.add("Your dedication is evident with each task you complete.");
        messages.add("Setting the bar high with every task! Impressive work.");
        messages.add("Tackling assignments like a pro! Keep it up.");
        messages.add("Consistent effort leads to success. Great job!");
        messages.add("Your hard work and commitment never go unnoticed.");
        messages.add("Pushing through and getting things done – that's the spirit!");
        messages.add("Achievement unlocked! Keep going.");
        messages.add("Every task completed is a step towards excellence.");
        messages.add("You're on a roll! Keep the streak going.");
        messages.add("Champion effort right there!");
        messages.add("Progress is made one assignment at a time. Well done!");
        messages.add("You're making waves with each submission!");
        messages.add("Keep setting the standard high!");
        messages.add("Your dedication is inspiring to us all.");
        messages.add("Another task conquered. You're unstoppable!");
        messages.add("Success is built on persistence. Keep it up!");
        messages.add("You're turning challenges into achievements!");
        messages.add("One more feather in your cap. Great job!");
        messages.add("Your journey of hard work is truly commendable.");
        messages.add("Excellence is a habit, and you're proving it!");
        messages.add("Keep up the fantastic work. The sky's the limit!");
        messages.add("You're setting a great example with every submission!");
        messages.add("Your efforts today shape your successes tomorrow.");
        messages.add("Every assignment is a testament to your dedication.");
        messages.add("You're making progress with every task. Keep going!");
        messages.add("Your commitment to growth is evident. Kudos!");
        messages.add("Another step closer to your goals. Well done!");
        messages.add("You're on the path to greatness. Keep moving forward!");
        messages.add("Every task completed is another victory. Cheers!");
        messages.add("You're building a legacy of hard work and determination.");
        messages.add("Your consistency is paving the way for success.");
        messages.add("Keep shining with every assignment you conquer!");
        messages.add("Your dedication today will lead to your achievements tomorrow.");
        messages.add("You're turning every challenge into an opportunity. Impressive!");
        messages.add("Keep pushing boundaries. Your efforts are commendable!");
        messages.add("Every submission is a reflection of your commitment. Great job!");
        messages.add("You're making every task count. Keep it up!");
        messages.add("Your journey is inspiring. Keep setting the pace!");
        messages.add("With every assignment, you're proving your mettle. Kudos!");
        messages.add("You're on a trajectory to success. Keep soaring!");
        messages.add("Every task you complete adds to your story of success.");
        messages.add("Your hard work today will shape your victories tomorrow.");
        messages.add("Keep up the momentum. You're doing great!");
        messages.add("Your dedication is shaping your path to success.");
        messages.add("With every submission, you're raising the bar. Impressive!");
        messages.add("You're turning dreams into realities with your hard work.");
        messages.add("Every assignment is a step closer to your aspirations. Well done!");
        messages.add("Your efforts are building a foundation for success.");
        messages.add("Keep making strides with every task. You're on the right track!");
        messages.add("Your commitment to excellence is truly inspiring.");
        messages.add("Every task you tackle is a testament to your determination.");
        messages.add("You're making a mark with every submission. Keep it up!");
        messages.add("Your journey of perseverance is commendable. Keep going!");
        messages.add("With every task, you're closer to your goals. Kudos!");
        messages.add("You're setting new standards with every assignment. Impressive!");
        messages.add("Your dedication and hard work are paving the way for success.");
        messages.add("Keep pushing forward. Every task is a step towards greatness!");
        messages.add("Your commitment to growth is evident in every submission.");
        messages.add("You're making progress with every assignment. Keep shining!");
        messages.add("Every task you complete is a victory in itself. Well done!");
        messages.add("Your journey is filled with achievements. Keep adding to it!");
        messages.add("You're setting a benchmark with every task. Keep it up!");
        messages.add("Your hard work and dedication are truly commendable.");
        messages.add("Keep making waves with every assignment. You're on a roll!");
        messages.add("Every submission is a testament to your commitment. Kudos!");
        messages.add("You're on the path to success. Keep making strides!");
        messages.add("Your dedication to every task is truly inspiring.");
        messages.add("Keep up the fantastic work. Your journey is commendable!");
        messages.add("Every assignment you complete adds to your legacy of hard work.");
        messages.add("You're making a difference with every task. Keep going!");
        messages.add("Your commitment to excellence is evident in every submission.");
        messages.add("Keep setting the pace. Your efforts are truly commendable!");
        messages.add("Every task you tackle is a step closer to your dreams.");
        messages.add("You're making a mark with every assignment. Keep shining!");
        messages.add("Your journey of hard work and dedication is truly inspiring.");
        messages.add("Keep up the momentum. Every submission counts!");
        messages.add("Your dedication to every task is setting a benchmark. Well done!");
        messages.add("You're on a trajectory to success. Keep making strides!");
        messages.add("Every assignment you complete is a testament to your hard work.");
        messages.add("Keep pushing boundaries. Your journey is commendable!");
        messages.add("You're turning challenges into achievements with every task.");
        messages.add("Your commitment to growth is evident in every assignment.");
        messages.add("Keep up the fantastic work. Every task is a step towards success!");
        messages.add("You're setting new standards with every submission. Impressive!");
        messages.add("Your hard work and dedication are shaping your path to greatness.");
        messages.add("Keep making waves with every task. You're on the right track!");
        messages.add("Every submission is a reflection of your commitment to excellence.");
        messages.add("You're making a mark with every assignment. Keep it up!");
        messages.add("Your journey of perseverance and hard work is truly commendable.");
        messages.add("Keep pushing forward. Every task is a victory in itself!");
        messages.add("Your dedication to every assignment is truly inspiring.");
        messages.add("Keep setting the pace. Your efforts are making a difference!");
        messages.add("Every task you complete is a testament to your determination.");
        messages.add("You're on the path to greatness. Keep making strides!");
        messages.add("Your hard work and commitment are evident in every submission.");
        messages.add("Keep up the momentum. Your journey is truly inspiring.");
    }
    public static String getHypeUpMessage () {
        Random rnd = new Random();
        
        int index = rnd.nextInt(100);
        return messages.get(index);
    }
}
