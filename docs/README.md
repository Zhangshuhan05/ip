# Charmie User Guide

![Screenshot of Charmie's interface](/docs/Ui.png)

Charmie is friendly cat chatbot that helps you manages your tasks! It can:

1. Add 3 different types of tasks - Todos, Deadlines and Events
2. Update the details of the tasks when needed
3. Mark tasks you have completed
4. Unmark tasks if wrongly marked
5. Delete the task when not needed
6. List down all the tasks you have added

Such a cool task manager, right? 
To use Charmie, here is the summary of all of Charmie's power:

## Features of Charmie 

### Add a new task 
There are 3 different tasks you can ask Charmie to manage for you!
1. Todos, task that you need to do but don't wanna give yourself a deadline
Format: ```ToDo [description of task]```

2. Deadlines, tasks that you need to do by a fixed timing
Format: ```Deadline [description of task] /by YYYY-MM-DD HHmm```

3. Events, tasks that require you to start and end in a certain duration
Format: ```Event [description of task] /from YYYY-MM-DD HHmm /to YYYY-MM-DD HHmm```

### Update a task 
Update your tasks if you decided to change any details about it!
You can change
- the description of all 3 tasks type **[/name]**
- the deadline **[/by]** of Deadline tasks
- the start **[/from]** and end **[/to]** timing of Event tasks

Format: ```update [index of task] [field] [changed input]```

You will get a confirmation response from Charmie after the task has been added successfully.

### Mark and unmark tasks
Mark and unmark your tasks based on your progress.

Mark - Format: ```mark [index of task]```

Unmark - Format: ```unmark [index of task]```

Charmie will record your tasks status automatically for you.

### Delete a task in the list 
Delete any unwanted tasks

Format: ```delete [index of task]```

Once deleted, you will have to add the task back manually if you want to again.

### View all task in one go
See all the tasks you have and their current completion status

Format: ```list```

### Leave Charmie
To leave Charmie when you're done managing your tasks of the day

Format: ```bye```
