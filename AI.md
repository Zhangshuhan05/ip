## Use of AI

I used ***copilot auto code completion*** to help with the creation of the child classes under parent class **Task**.

After writing the abstact parent class, copilot helped to complete some of the methods that need overriding as well as the attributes that my child class might need.
While it saves a lot of time to write down the mundane information, the auto code completion sometimes gives the wrong names for the inputs or their method logic doesn't cover
all cases that my methods need to address. So modifications have to be done after wards to ensure all logic works.

I also used the help of ***ChatGPT*** to check the accuracy of my code.

When I was adding an extension, the **update** feature, I wrote two helper methods, one for parsing the field to edit and one for the information to edit.
After checking with ChatGPT, I realised a few mistakes in my loops that might lead to errors and was able to edit them in time. It also suggected that I combine the two helper
methods into one, where instead of returning a String each, I return a Pair of Strings which was something I overlooked.
I adopted the suggested code and it seems to work well. Based on my past experience generating code with ChatGPT, they give pretty good ideas for improving
efficiency of code, but whether their logic will work is not guarenteed, so edits need to be done and their suggested code cannot be taken wholesale.