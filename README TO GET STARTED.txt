HOW AND WHERE TO EDIT

go to:
    - src>main>java>com.example.gpass

MainApplication = basic UI
MainController = the java commands behind each button's actions

go to:
    - src>main>java>resources>con.example...>

mainWindowControls = The UI of the MainApplication. This is where you add buttons etc.

==========================================================================================
NOTE:
If you need to add another page or anything then feel free to change or create more pathways or anything.
I did start the basic UI (including the UI for Login/Register)
==========================================================================================

Things to DO:
1. Login / Register of users - ?
2. Enable users to upload and delete content (Photos) - ?
3. Enable profiles to access the user's information (photos included) - Ethan (Need register and upload to be finished first however)
4. Design how they are to be placed - Ethan





=============================================================================================
WHERE TO START????

1. Open the project
2. Check if you are connected
    - git status
    - git branch
    // the one that is green is the branch you are on
3. if fails to connect or errors happen follow the below script

    git init //initialises entry
    git commit -m "first commit - your name //tells the server you are joining.
    git branch -M main //depending on if you want to enter the main branch (recommended if its the first time joining)
    git remote add origin https://github.com/TheOostman/Art-Portfolio-Manager_Project.git //double checks to ensure connection
    git push -u origin main //pushes content
    git pull //pulls any content that people have updated recently

4. If you are creating something and are unsure if it will break the system please create
    a new branch. If it works fine then you may merge the branch into the main.

    git branch newBranch //change newBranch into what you want to call it
    git branch //check to see if you are in the correct branch (should be green) if not then
    git checkout newBranch //move into the new branch

5. Now do the work you wanted to do
6. Send the updated version to git.
    git add .     //this adds all new content
    git commit -m "i worked on....  - your name"    //sends a update message
    git push -u origin main     //main for if you working on main else use the other branch you are working on. This pushes
                                    the content onto git

7. IF YOU WANT TO MERGE BRANCHES
    git checkout main   //go to the branch you want to merge into
    git merge branch2   //branch2 for example is another branch we want to merge into main


test

