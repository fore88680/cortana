/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.numberguessfinal.dao;

import com.sg.numberguessfinal.model.Round;
import java.util.List;

/**
 *
 * @author fore8
 */
public interface RoundDao {
    
    public Round addRound(Round round);
    public void deleteRound(int roundId);
    public void updateRound(Round round);
    public Round getRoundById(int roundId);
    public List<Round>getAllRounds();
    
}
