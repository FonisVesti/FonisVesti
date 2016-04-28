package com.vesti.fonis.fonisvesti.model;

import java.util.LinkedList;

/**
 * Created by Dusan on 27.4.2016..
 */
public class Projects {
    public static LinkedList<Project> projects=new LinkedList<>();

    public static void loadProjects(){
    projects.add(new Project("Studenti studentima", "“Studenti Studentima” je tradicionalni projekat Udruženja koji podrazumeva organizovanje i održavanje niza interaktivnih kurseva koje drže studenti FON-a i drugih fakulteta na različite teme iz oblasti informacionih sistema i tehnologija. Tokom niza godina izvođenja projekta, studentima FON-a omogućeno je da učenjem od svojih kolega dobiju znanja o upotrebi raznih softverskih paketa, operativnih sistema i modernih tehnologija. Cilj projekta „Studenti studentima“ je održavanje interaktivnih kurseva iz oblasti primenjenih tehnologija, kao i iz gradiva smera Informacioni sistemi (programski jezici, informatička pismenost, softverski paketi za web dizajn, grafičku obradu slike i zvuka).\n" +
            "\n" +
            "U okviru projekta „Studenti studentima“, u saradnji sa fakultetom, studentima i kompanijama, FONIS je od svog osnivanja organizovao niz uspešnih kurseva, među kojima su Linux, PHP, Dreamweaver, Corel Draw, Photoshop, Flash, 3ds max i mnogi drugi, koje je pohađalo na stotine zadovoljnih studenata. FONIS će i u budućnosti nastaviti da pruža svojim članovima usavršavanje organizovanjem različitih kvalitetnih kurseva u zavisnosti od interesovanja. Pored toga, FONIS će redovno ispitivati interesovanje studenata za određenim kursevima i na taj način planirati buduće aktivnosti.\n" +
            "\n" +
            "Do sada su održani kursevi na čak nekoliko desetina tema. Java, Java advanced , JAVA II – napredno programiranje, Flash, Flash Advanced, C# , ECDL , Photoshop CS4, Linux, Osnovni HTML 1, Napredni HTML, MySQL, PHP, Joomla, Flash CS5, Photoshop CS5, HTML i CSS, Mercurial, Haskell, WordPress, Haskell – Funkcionalno programiranje, phpBB Forum, Osnove LINQ-a, Excel-advanced, ADOBE PREMIER PRO , PHP I MySQL, Symfony, Osnovi jezika C++ i algoritama, Google AdWords – Search Engine Marketing, Uvod u C# – Windows forms application, Algoritmi – dizajn i analiza, Symfony framework, Node.js, Ember.js, C, razvoj igrica u C#-u, Osnove Android platforme.", null));
    projects.add(new Project("FON Hakaton 2014","Po drugi put na Fakultetu organizacionih nauka udruženje studenata informatike FONIS organizovalo je takmičenje FON Hakaton u formi hakatona od 24 sata pod sloganom „Can you do IT?“ u saradnji sa kompanijom Microsoft Srbija. Takmičenje je bilo namenjeno svim studentima Fakulteta organizacionih nauka sa ciljem da se, kroz izradu aplikacije na zadatu temu u tehnologiji po izboru, pomogne studentima FON-a da na što lakši način dođu do posla nakon završetka studija.\n" +
            "\n" +
            "Zadatak takmičara bio je da osmisle „FON alumni menadžment sistem“, vrstu poslovne mreže za studente FON-a, sa profilima studenata i njihovim znanjima i kvalifikacijama. Bilo je potrebno implementirati određene korisničke zahteve, a jedan od zadataka je bio i idejno rešavanje određenih problema. Upotrebljivost, inovativnost, dizajn i kvalitet koda aplikacija ocenjivao je stručni žiri. Prva tri mesta su vredno nagrađena.",null));
    projects.add(new Project("FON Hakaton 2013","Projekat FON Hakaton je jedini do sada održani projekat tog tipa na Fakultetu organizacionih nauka. Pravo učešća su imali svi sadašnji i bivši studenti Fakulteta organizacionih nauka.\n" +
            "\n" +
            "Cilj projekta je bila izrada aplikacije u proizvoljnoj tehnologiji koja će direktno pomoći studentima Fakulteta organizacionih nauka. Tema je bila “Sistem zakazivanja termina i naručivanja uverenja studentske službe”. Nakon 24h izrade, usledilo je predstavljanje aplikacija žiriju koji je ocenjivao projekat sa ocenama od jedan do pet. Sistem ocenjivanja je bio zasnovan po broju bodova datih od strane žirija. Prva tri mesta dobijala su simbolične nagrade.",null));
    }
}
