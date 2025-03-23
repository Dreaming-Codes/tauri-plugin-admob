# Tauri Plugin Admob Contributing Guide

Hi! We, the maintainers, are really excited that you're interested in contributing to Tauri Plugin Admob. Before submitting your
contribution though, please make sure to take a moment and read through the [Code of Conduct](CODE_OF_CONDUCT.md), as
well as the appropriate section for the contribution you intend to make:

- [Issue Reporting Guidelines](#issue-reporting-guidelines)
- [Development Guide](#development-guide)
- [Pull Request Guidelines](#pull-request-guidelines)

**We strongly advise you to join the [DreamingCodes Discord](https://discord.gg/MehYjUJGpA) to discuss your contribution
with the community.**

## Issue Reporting Guidelines

- The issue list of this repo is **exclusively** for bug reports and feature requests. Non-conforming issues will be
  closed immediately.

- If you have a question, you can get quick answers from the [DreamingCodes Discord](https://discord.gg/MehYjUJGpA).

- Try to search for your issue, it may have already been answered or even fixed in the development branch (`main`).

- Check if the issue is reproducible with the latest stable version. If you're using a pre-release, please
  indicate the specific version you're using.

- It is **required** that you clearly describe the steps necessary to reproduce the issue you're running into. Although
  we would love to help our users as much as possible, diagnosing issues without clear reproduction steps is extremely
  time-consuming and simply not sustainable.

- Use only the minimum amount of code necessary to reproduce the unexpected behavior. A good bug report should isolate
  specific methods that exhibit unexpected behavior and precisely define how expectations were violated. What did you
  expect the method or methods to do, and how did the observed behavior differ? The more precisely you isolate the
  issue, the faster we can investigate.

- Issues with no clear repro steps will not be triaged. If an issue labeled "need repro" receives no further input from
  the issue author for more than 5 days, it will be closed.

- If your issue is resolved but still open, don’t hesitate to close it. In case you found a solution by yourself, it
  could be helpful to explain how you fixed it.

- Most importantly, we beg your patience: the team must balance your request against many other responsibilities —
  fixing other bugs, answering other questions, new features, new documentation, etc. The issue list is not paid
  support, and we can't make guarantees about how fast your issue can be resolved.

## Development Guide

Is this repository we stick to the following branch structure:

| Branch | Description |
|-|-|
| `main` | The develop branch is where all the working branches are merged. It's not possible to push code directly here, not even for a maintainer. Everything in `main` must come from a pull request after a code review. |
| `<working>` | Working branches are always created from `main` and to `main` shall return! Only working branches should be merged in to the develop branch after a code review. |

## Pull Request Guidelines

You must read the [Development Guide](#development-guide) section before proceeding with the pull request guidelines.

In this project we stick to the following naming convention for the Pull Request title:

```yml
<type>: <short summary>
  │            │
  │            └─⫸ Summary: Present tense. Not capitalized. No period at the end.
  │
  └─⫸ Type: build|ci|docs|feat|fix|perf|refactor
```

##### Type must be one of the following:
>* **build**: Changes that affect the build system or external dependencies (example scopes: gradle)
>* **ci**: Changes to our CI configuration files and scripts (examples: Github Actions)
>* **docs**: Documentation only changes
>* **feat**: A new feature
>* **fix**: A bug fix
>* **perf**: A code change that improves performance
>* **refactor**: A code change that neither fixes a bug nor adds a feature

##### Summary
>Use the summary field to provide a succinct description of the change:
>
>* use the imperative, present tense: "change" not "changed" nor "changes"
>* don't capitalize the first letter
>* no dot (.) at the end

**You must follow this convention also for the working branch name: `<type>/<summary>`, e.g. `build/migrate_gradle`.**

##### Some clarifications
>- It's OK to have multiple small commits as you work on the PR – We will let GitHub automatically squash it before
   > merging. (That is why we're restrictive about the title of the PR)
>
>- If adding new feature:
   >
   >    - Provide a convincing reason to add this feature. Ideally, you should open a suggestion issue first and have it
          > greenlighted before working on it.
