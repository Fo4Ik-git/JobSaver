# JobSaver

[![Download Latest](https://img.shields.io/github/v/release/Fo4Ik-git/JobSaver?label=Download%20Latest)](https://github.com/Fo4Ik-git/JobSaver/releases/latest)

[![License: License](https://img.shields.io/badge/License-JobSaver-brightgreen?label=License)](LICENSE)


**JobSaver** is an app that helps you keep track of your job applications. You can add jobs that you are interested in,
check their responsibilities, and update their status. JobSaver also lets you see the progress of your applications in a
dashboard. With JobSaver, you can easily manage your job search and never miss an opportunity.

## Features

- **LinkedIn Integration**: JobSaver currently supports job searching and saving from LinkedIn, with plans to extend
  support for other job search platforms in the future.

## Getting Started

#### To run the project in an IDE, follow these steps:

1. Clone the repository to your local machine.

   ```bash
   git clone https://github.com/Fo4Ik-git/JobSaver.git
   ```

2. Open the project in your IDE of choice.
3. Run the `Main` class.


#### To build and run the project, you can use the provided `build.gradle` file. Follow these steps:

1. Clone the repository to your local machine.

   ```bash
   git clone https://github.com/Fo4Ik-git/JobSaver.git
   ```

2. Open a terminal or command prompt and navigate to the project's root directory.

3. Run the following command to build the project using Gradle:

   ```bash
   gradlew jpackage
   ```

4. Once the build is complete, you can run the application you can find in the `build/jpackage` directory.

## Usage

**JobSaver** offers the following main components:

### Main Application Window

- The application's main window serves as the central hub for job search and management.
- Users can view their saved job listings and perform various actions such as editing and deleting jobs.

### Job Search

- The application allows users to search for job listings on LinkedIn by providing search queries.
- Users can enter keywords related to their job search and initiate the search from the top panel.

### Saved Job Listings

- Users can view their saved job listings in a list format within the main window.
- Each listing includes job title, company, and status information.
- Clicking on a job listing opens a detailed view of the job in a split-panel format.

### Job Details

- The detailed view of a job listing includes the job's web page displayed within the application.
- Users can also edit job details such as title, company, and status directly from this view.
- Changes made to job details can be saved or discarded.

## License

Please refer to the [LICENSE](LICENSE) file in the repository for licensing information.

## Note

**JobSaver** is continually evolving, with plans to expand its capabilities to support additional job search platforms
in the future. Stay tuned for updates and enhancements to make your job search experience even more efficient and
convenient.


