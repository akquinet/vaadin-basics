package de.akquinet.engineering.vaadin.exercises.grid;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;

/**
 * @author Axel Meier, akquinet engineering GmbH
 */
public class Player
{
    private String name;
    private LocalDate dateOfBirth;
    private Gender gender;
    private long points;
    private int medals;

    public Player()
    {
    }

    public Player(final String name, final LocalDate dateOfBirth,
                  final Gender gender, final long points, final int medals)
    {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.points = points;
        this.medals = medals;
    }

    public LocalDate getDateOfBirth()
    {
        return dateOfBirth;
    }

    public void setDateOfBirth(final LocalDate dateOfBirth)
    {
        this.dateOfBirth = dateOfBirth;
    }

    public int getAge(){
        final LocalDate now = LocalDate.now(ZoneId.of("UTC"));
        return Period.between(dateOfBirth, now).getYears();
    }

    public String getName()
    {
        return name;
    }

    public void setName(final String name)
    {
        this.name = name;
    }

    public Gender getGender()
    {
        return gender;
    }

    public void setGender(final Gender gender)
    {
        this.gender = gender;
    }

    public long getPoints()
    {
        return points;
    }

    public void setPoints(final long points)
    {
        this.points = points;
    }

    public int getMedals()
    {
        return medals;
    }

    public void setMedals(final int medals)
    {
        this.medals = medals;
    }

    @Override
    public String toString()
    {
        return "Player{" +
                "name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", gender=" + gender +
                ", points=" + points +
                ", medals=" + medals +
                '}';
    }
}
