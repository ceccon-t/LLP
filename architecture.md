# LLP - Architecture

This file describes the overall architecture of the project, including its most important design decisions, code structure and frameworks used.


## Design decisions

An important early decision (or "axiom") for this project was to try to avoid as much as possible frameworks that offered an API that is too abstract. Instead, the idea was to focus on building by hand most of the relevant logic, as the project itself was an exercise in getting familiar with interacting with LLMs.

Another axiom is to work only with local models. While frontier models would likely generate better results, the purpose of the project is learning and exploring what can be achieved with models that can be owned by the users.


## Structure

### Project meta files

The main folder of the project is named `LLP`, and it is present at the root of the repository. Also at the root, `README.md` gives a short presentation at a project level, while this `architecture.md` file should quickly put any developer up to speed as to how the code is laid out. The `images` folder at root level contains images used only in the presentation of the repository, not to be used inside the application. The `samples` folder contains relevant samples to serve as reference, with special attention to `samples/default_session/default.practice`, which can be used as a model for defining custom scenes which can later be loaded for practice inside the application.

### Code overview

The main class of the application is at the root package, `LLP`.

There are two main "things" one can do in the application: having a conversation with an LLM and translating segments of English text to and from the language currently being practiced. Each of these have a package and a service class of their own: `conversation` and `translation`.

Package `config` holds configuration both for the API being used as backend and for the current practice session. Package `practice` contains most of the classes that model the practice activity itself. Package `client` is mostly a technical utility for handling requests to the LLM server, while package `gui` contains all the UI elements (including their behavior).

#### Package `client`

Util classes to handle all the communication with LLM server.

#### Package `config`

Configuration classes for the application. `LLMAPIConfig` holds the configuration for the requests to the LLM server, and is not intended to be much more than a POJO.

`PracticeSessionConfig` holds all the configuration for the current practice session, and it is seen as the main "source of truth" by most other parts of the application. This makes it somewhat of a God class, but given the current scale of the project the benefits of having a central point that all other parts can reach out to for the current state has so far proven worthwhile. This approach might change in the future, should the application grow in a way that tips the balance towards the current design being a hassle. The class is used as a singleton.

A couple of interfaces are also present to define observers for different changes in configuration, mostly used by the UI elements to reconfigure themselves appropriately when necessary. The central observed subject is the single instance of PracticeSessionConfig.

#### Package `conversation`

Classes that model the conversation, as well as a service class which encapsulates the requests to the LLM server and add instructions about the expected answers to improve the quality of the responses.

#### Package `gui`

All UI elements. The interaction behavior (handling methods) for the elements tends to be present in the class that holds them, delegating to more inner layers when necessary.

#### Package `practice`

Several POJO(-ish) classes and enums to model the practice activity.

#### Package `translation`

Responsible for handling the translation part of the application. Currently, only contains a single service class that adds instructions to the LLM model about the desired result.

### Prompts

For those interested in checking the prompts that are being used to try and steer the LLM model responses towards the desired results, those can usually be found on the service classes, `ConversationService` (contains the prompt instructing the LLM how to provide the next answer in the conversation while staying in-character and using the practiced language) and `TranslationService` (contains the prompt to instruct the LLM to just translate the provided segment of text to the expected language, without adding commentary or other undesired text).

Also, the image generation prompts used to generate the character images for the default scene can be found outside of the main project folder, under `samples/default_session/character_images`.


## CI/CD

The project uses Github Actions to automatically generate a new release whenever new changes that alter the application itself are pushed into the `main` branch.

If automated tests fail or the build breaks, a red failure sign is displayed near the hash of the commit in the repository. If all goes well, a green success sign is displayed instead. A badge with the status of the latest build for the main branch is also displayed in the Readme of the project.

The script that defines the main workflow can be found under `.github/workflows/main-workflow.yml`.


## Libraries and Frameworks

[JCommander](https://jcommander.org/) for parsing command line options.

[Maven](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html) as a build automation tool.

[JUnit](https://junit.org/junit5/docs/current/user-guide/) and [Mockito](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html) for automated tests.

[Swing](https://docs.oracle.com/javase/tutorial/uiswing/) as the GUI framework.

[Github Actions](https://docs.github.com/en/actions/learn-github-actions) for continuous integration and continuous delivery.

