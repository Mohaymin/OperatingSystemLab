#include<bits/stdc++.h>
using namespace std;

int main()
{
    freopen("input.txt", "r", stdin);
    int totalProcess, totalResource;

    cout << "Enter total number of resources\n";
    cin >> totalResource;
    cout << totalResource;
    cout << "\nEnter total number of processes\n";
    cin >> totalProcess;
    cout << totalProcess;

    vector<int> resources;
    cout << "\nEnter the resources\n";
    int dummy;
    for(int i=0; i<totalResource; i++)
    {
        cin >> dummy;
        cout << dummy << ' ';
        resources.push_back(dummy);
    }

    int allocatedMemory[totalProcess+1][totalResource+1];
    cout << "\nEnter allocated resources for each processes\n";
    for(int i=0; i<totalProcess; i++)
    {
        for(int j=0; j<totalResource; j++)
        {
            cin >> allocatedMemory[i][j];
            cout << allocatedMemory[i][j] << ' ';
        }
        cout << '\n';
    }
    int sum;
    for(int i=0; i<totalResource; i++)
    {
        sum = 0;
        for(int j=0; j<totalProcess; j++)
        {
            sum += allocatedMemory[j][i];
        }
        allocatedMemory[totalProcess][i] = sum;
    }

    int maximumRequirement[totalProcess+1][totalResource+1];
    cout << "\nEnter maximum required resources for each processes\n";
    for(int i=0; i<totalProcess; i++)
    {
        for(int j=0; j<totalResource; j++)
        {
            cin >> maximumRequirement[i][j];
            cout << maximumRequirement[i][j] << ' ';
        }
        cout << '\n';
    }

    /// calculate the needed resources
    int need[totalProcess+1][totalResource+1];
    cout << "\nNeeded resources\n";
    for(int i=0; i<totalProcess; i++)
    {
        for(int j=0; j<totalResource; j++)
        {
            need[i][j] = maximumRequirement[i][j]-allocatedMemory[i][j];
            cout << need[i][j] << ' ';
        }
        cout << '\n';
    }

    /// calculate the resources available for work
    int workResource[totalResource];
    for(int i=0; i<totalResource; i++)
    {
        workResource[i] = resources[i]-allocatedMemory[totalProcess][i];
    }
    cout << "\nResources available for work\n";
    for(int i : workResource)
    {
        cout << i << ' ';
    }
    cout << '\n';

    /// initialize the finish marker
    bool finish[totalProcess];
    for(int i=0; i<totalProcess; i++)
    {
        finish[i] = false;
    }

    /// generate the sequence
    vector<int> ans;
    bool shallTerminate = false;
    bool shallChoose;
    for(int k=0; k<=totalProcess && !shallTerminate; k++)
    {
        shallTerminate = true;
        for(int i=0; i<totalProcess; i++)
        {
            if(!finish[i])
            {
                shallChoose = true;
                for(int j=0; j<totalResource; j++)
                {
                    if(need[i][j] > workResource[j])
                    {
//                        cout << "Error at " << i << ' ' << j << endl;
                        shallChoose = false;
                        break;
                    }
                }
                if(shallChoose)
                {
//                    cout << i << " is chosen " << endl;
                    for(int j=0; j<totalProcess; j++)
                    {
                        workResource[j] += allocatedMemory[i][j];
                    }
                    finish[i] = true;
                    ans.push_back(i);
                }
            }
        }
    }
    if(ans.size()<totalProcess)
    {
        cout << "\nThere is no safe sequence\n";
    }
    else
    {
        cout << "\nSafe sequence:\n";
        for(int p : ans)
        {
            cout << "P" << p+1 << ' ';
        }
        cout << '\n';
    }
}
