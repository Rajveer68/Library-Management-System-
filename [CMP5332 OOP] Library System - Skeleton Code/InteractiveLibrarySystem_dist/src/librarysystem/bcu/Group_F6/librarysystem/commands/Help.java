package bcu.Group_F6.librarysystem.commands;

import bcu.Group_F6.librarysystem.model.Library;

import java.time.LocalDate;

public class Help implements Command {

    @Override
    public void execute(Library library, LocalDate currentDate) {
        System.out.println(Command.HELP_MESSAGE);
    }
}